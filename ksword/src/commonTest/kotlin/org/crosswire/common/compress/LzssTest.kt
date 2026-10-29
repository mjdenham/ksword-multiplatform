package org.crosswire.common.compress

import kotlin.io.encoding.Base64
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class LzssTest {

    @Test
    fun decodesRealSwordBlock() {
        val decoded = Lzss.uncompress(Base64.decode(SPARVG_2JOHN_BLOCK))
        assertEquals(SPARVG_2JOHN_VERSES.joinToString(""), decoded.decodeToString())
    }

    @Test
    fun emptyInput() {
        assertContentEquals(ByteArray(0), Lzss.uncompress(ByteArray(0)))
    }

    @Test
    fun literals() {
        assertEquals("abc", Lzss.uncompress(bytes(0x07, 'a'.code, 'b'.code, 'c'.code)).decodeToString())
    }

    @Test
    fun referenceIntoInitialSpaces() {
        assertEquals("   ", Lzss.uncompress(bytes(0x00, 0x00, 0x00)).decodeToString())
    }

    @Test
    fun overlappingReferenceRepeatsRun() {
        // literal 'a' at ring position 4078, then copy 5 from 4078 while writing from 4079
        assertEquals("aaaaaa", Lzss.uncompress(bytes(0x01, 'a'.code, 0xEE, 0xF2)).decodeToString())
    }

    @Test
    fun truncatedReferenceIsDropped() {
        assertEquals("a", Lzss.uncompress(bytes(0x01, 'a'.code, 0xEE)).decodeToString())
    }

    private fun bytes(vararg values: Int) = ByteArray(values.size) { values[it].toByte() }

    companion object {
        // SpaRVG nt.bzz block 23 (2 John) from CrossWire; verses below match SWORD's mod2imp output.
        private const val SPARVG_2JOHN_BLOCK =
            "/0VsIGFuY2lh/25vIGEgbGEg/3Nlw7FvcmEg/2VsZWdpZGEg/Xn48HN1cyBoae9qb3Ms+PBxdWm/ZW5lcyB59/Bt728gZW768XZl" +
            "cs9kYWQ7DQD28HPD97NsbyYALCBzaf728HRhbWJpw6n/biB0b2RvcyD9bFwAcXVlIGhh/24gY29ub2Np+2RvMAcscG9yIP9jYXVz" +
            "YSBkZdQwB2ICbQICbj0Ac2/7dHIZAXkgZXN0f2Fyw6EgcGEDAH9zaWVtcHJlagGeoAYuR3Jh8/D98GH6wQJ2ogZtaXNlcl9pY29y" +
            "ZNMAebQA23oshwFEaVwAUGF1ZL8AeYcAbCBT//L/IEplc3VjcmmLc3RIAEgXAAsSBBIs6C0BjgSrAG4pAXIuTf91Y2hvIG1lIPdy" +
            "ZWduAGrDqSA4fgBjAmUBbGxhcQCIADl0EwdjAWFuZGgALgmFLE8AbGoAKwChBWAQbT5cAHJlY2licAENENVtfhFtIQB0JhguWVcg" +
            "YWgCAXROEHVREGRJAP/zLD0BlhJlcxsQtlMCZG/UEHVutRpuV3VldkgFYWMAbGID7qUSdGVucAFkZXPciAANEHByafLwcGmgSABj" +
            "AaEAKQClEnVIIiASyQNZrAE0IHOyEUIReBZ+pRJzZWfDum4RAp62GHMuIEVjJ7YYOrsgUXsTw6lpaCBuHyDDqWwslRPeBWYAnWK3" +
            "Im/DrS0vPyAu5VBbE21IEbohZ2HDRbFoEHIkAGcBvRByaBHULgCzEXX4ECxeAmN182FsJADnEm5maWUBc2gAYwEXF2YAMwArIi4A" +
            "H2Nhcm5lliAdI5IRXTwUMG5zQ2cAZ20Q/3lwZT0iYWRk/2VkIj5oYWNlizwvbjg+ZiICNg0Bbjt0aRsTLk1pFTBZEcjIJ+gAphAs" +
            "tANEIyBwgDUBphEZIkQAfgFEAB8ocg9hYmFqaBBJBGMBqhL+4DJnYWxhcmTDvbNpAW1wbGWyMEPoLDAfAdI0c04RYmVsGOQQPALc" +
            "MHNlNACcAvvwT2RvY3Q6IIYCQxwUEE0BIQH48P8BO7IRYwFOT0BgTw0QkhF1RCoUDQFsIhLXLlNpukBnUyAgdiB2RMkmPAIUMGUh" +
            "dJhH5RKMRACqEsOhuSNtP381du11rQByYYs8Y2Fz/uQRaSBsZSBkaR1nDVE6IEIhAFEz8iWMHCROUmNlWFnOMqwwadlwhgKGI2xh" +
            "2CBicj9hcy5BdW5lMgEwOEsQSBCcUGNvc5xQaFIg8BOmAvbwYRBjAHJwARVfzn85cmxvizx+AW1lu2RpahJwYXANEHl+dEBudGE7" +
            "IHAwUDysANwwbyBpct1L0iCEHkBaMWH48ExhzjkwUm9PIGdvej8A1wF1KEBCYVFMoREXAWsTYBBythC8BAfUEHNhbHWAEC4XIEFt" +
            "VQAu"

        private val SPARVG_2JOHN_VERSES = listOf(
        "El anciano a la señora elegida y a sus hijos, a quienes yo amo en la verdad; y no sólo yo, sino también todos los que han conocido la verdad,",
        "por causa de la verdad que mora en nosotros, y estará para siempre con nosotros.",
        "Gracia sea con vosotros, misericordia y paz, de Dios Padre y del Señor Jesucristo, Hijo del Padre, en verdad y en amor.",
        "Mucho me regocijé porque he hallado de tus hijos, que andan en la verdad, tal como nosotros hemos recibido el mandamiento del Padre.",
        "Y ahora te ruego, señora, no como escribiéndote un mandamiento nuevo, sino aquel que hemos tenido desde el principio, que nos amemos unos a otros.",
        "Y este es el amor, que andemos según sus mandamientos. Este es el mandamiento: Que andéis en él, como vosotros habéis oído desde el principio.",
        "Porque muchos engañadores han entrado en el mundo, los cuales no confiesan que Jesucristo ha venido en carne. El que tal <transChange type=\"added\">hace</transChange> es engañador y anticristo.",
        "Mirad por vosotros mismos, para que no perdamos aquello por lo que hemos trabajado, sino que recibamos galardón completo.",
        "Cualquiera que se rebela, y no persevera en la doctrina de Cristo, no tiene a Dios; el que persevera en la doctrina de Cristo, el tal tiene al Padre y al Hijo.",
        "Si alguno viene a vosotros y no trae esta doctrina, no lo recibáis en <transChange type=\"added\">vuestra</transChange> casa, ni le digáis: Bienvenido.",
        "Porque el que le dice: Bienvenido, participa de sus malas obras.",
        "Aunque tengo muchas cosas que escribiros, no he querido <transChange type=\"added\">hacerlo</transChange> por medio de papel y tinta; pues espero ir a vosotros y hablar cara a cara, para que nuestro gozo sea cumplido.",
        "Los hijos de tu hermana elegida te saludan. Amén."
        )
    }
}
