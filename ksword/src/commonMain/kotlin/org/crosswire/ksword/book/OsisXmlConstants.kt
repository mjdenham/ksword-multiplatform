package org.crosswire.ksword.book

object OsisXmlConstants {
    private const val SPACE_SEPARATOR = ' '
    private const val MORPH_INFO_SEPARATOR = '@'

    /**
     * The following are values for the type attribute on the hi element.
     */
    /**
     * Constant for acrostic highlighting
     */
    const val HI_ACROSTIC: String = "acrostic"

    /**
     * Constant for rendering bold text
     */
    const val HI_BOLD: String = "bold"

    /**
     * Constant for rendering emphatic text
     */
    const val HI_EMPHASIS: String = "emphasis"

    /**
     * Constant for rendering illuminated text.
     */
    const val HI_ILLUMINATED: String = "illuminated"

    /**
     * Constant for rendering italic text.
     */
    const val HI_ITALIC: String = "italic"

    /**
     * Constant for rendering strike-through text
     */
    const val HI_LINETHROUGH: String = "line-through"

    /**
     * Constant for rendering normal text.
     */
    const val HI_NORMAL: String = "normal"

    /**
     * Constant for rendering small caps
     */
    const val HI_SMALL_CAPS: String = "small-caps"

    /**
     * Constant for rendering subscripts
     */
    const val HI_SUB: String = "sub"

    /**
     * Constant for rendering superscripts
     */
    const val HI_SUPER: String = "super"

    /**
     * Constant for rendering underlined text
     */
    const val HI_UNDERLINE: String = "underline"

    /**
     * Constant for rendering upper case text
     */
    const val HI_X_CAPS: String = "x-caps"

    /**
     * Constant for rendering big text
     */
    const val HI_X_BIG: String = "x-big"

    /**
     * Constant for rendering small text
     */
    const val HI_X_SMALL: String = "x-small"

    /**
     * Constant for rendering tt text
     */
    const val HI_X_TT: String = "x-tt"

    /**
     * Constant to help narrow down what we use seg for. In this case the
     * justify right tag
     */
    const val SEG_JUSTIFYRIGHT: String = "text-align: right;"

    /**
     * Constant to help narrow down what we use seg for. In this case the
     * justify right tag
     */
    const val SEG_JUSTIFYLEFT: String = "text-align: left;"

    /**
     * Constant to help narrow down what we use seg for. In this case the thml
     * center tag
     */
    const val SEG_CENTER: String = "text-align: center;"

    /**
     * Constant to help narrow down what we use div for. In this case the thml
     * pre tag
     */
    const val DIV_PRE: String = "x-pre"

    /**
     * Constant to help narrow down what we use seg for. In this case the color
     * tag
     */
    const val SEG_COLORPREFIX: String = "color: "

    /**
     * Constant to help narrow down what we use seg for. In this case the
     * font-size tag
     */
    const val SEG_SIZEPREFIX: String = "font-size: "

    /**
     * Constant for x- types
     */
    const val TYPE_X_PREFIX: String = "x-"

    /**
     * Constant for the study note type
     */
    const val NOTETYPE_STUDY: String = "x-StudyNote"

    /**
     * Constant for the cross reference note type
     */
    const val NOTETYPE_REFERENCE: String = "crossReference"

    /**
     * Constant for the variant type segment
     */
    const val VARIANT_TYPE: String = "x-variant"
    const val VARIANT_CLASS: String = "x-"

    /**
     * Constant for KSword generated content. Used for type or subType.
     */
    const val GENERATED_CONTENT: String = "x-gen"

    /**
     * Constant for the pos (part of speech) type.
     */
    const val POS_TYPE: String = "x-pos"

    /**
     * Constant for the def (dictionary definition) type
     */
    const val DEF_TYPE: String = "x-def"

    /**
     * Constant for a Strong's numbering lemma
     */
    const val LEMMA_STRONGS: String = "strong:"
    const val LEMMA_MISC: String = "lemma:"
    const val MORPH_ROBINSONS: String = "robinson:"

    /**
     * Constant for Strong's numbering morphology
     */
    const val MORPH_STRONGS: String = "x-StrongsMorph:T"

    /**
     * Constant to help narrow down what we use "q" for. In this case:
     * blockquote
     */
    const val Q_BLOCK: String = "blockquote"

    /**
     * Constant to help narrow down what we use "q" for. In this case: citation
     */
    const val Q_CITATION: String = "citation"

    /**
     * Constant to help narrow down what we use "q" for. In this case: embedded
     */
    const val Q_EMBEDDED: String = "embedded"

    /**
     * Constant to help narrow down what we use "list" for.
     */
    const val LIST_ORDERED: String = "x-ordered"
    const val LIST_UNORDERED: String = "x-unordered"

    /**
     * Table roles (on table, row and cell elements) can be "data", the default,
     * or label.
     */
    const val TABLE_ROLE_LABEL: String = "label"

    /**
     * Possible cell alignments
     */
    const val CELL_ALIGN_LEFT: String = "left"
    const val CELL_ALIGN_RIGHT: String = "right"
    const val CELL_ALIGN_CENTER: String = "center"
    const val CELL_ALIGN_JUSTIFY: String = "justify"
    const val CELL_ALIGN_START: String = "start"
    const val CELL_ALIGN_END: String = "end"

    const val OSIS_ELEMENT_ABBR: String = "abbr"
    const val OSIS_ELEMENT_TITLE: String = "title"
    const val OSIS_ELEMENT_TABLE: String = "table"
    const val OSIS_ELEMENT_SPEECH: String = "speech"
    const val OSIS_ELEMENT_SPEAKER: String = "speaker"
    const val OSIS_ELEMENT_ROW: String = "row"
    const val OSIS_ELEMENT_REFERENCE: String = "reference"
    const val OSIS_ELEMENT_NOTE: String = "note"
    const val OSIS_ELEMENT_NAME: String = "name"
    const val OSIS_ELEMENT_Q: String = "q"
    const val OSIS_ELEMENT_LIST: String = "list"
    const val OSIS_ELEMENT_P: String = "p"
    const val OSIS_ELEMENT_ITEM: String = "item"
    const val OSIS_ELEMENT_FIGURE: String = "figure"
    const val OSIS_ELEMENT_FOREIGN: String = "foreign"
    const val OSIS_ELEMENT_W: String = "w"
    const val OSIS_ELEMENT_CHAPTER: String = "chapter"
    const val OSIS_ELEMENT_VERSE: String = "verse"
    const val OSIS_ELEMENT_CELL: String = "cell"
    const val OSIS_ELEMENT_DIV: String = "div"
    const val OSIS_ELEMENT_OSIS: String = "osis"
    const val OSIS_ELEMENT_WORK: String = "work"
    const val OSIS_ELEMENT_HEADER: String = "header"
    const val OSIS_ELEMENT_OSISTEXT: String = "osisText"
    const val OSIS_ELEMENT_SEG: String = "seg"
    const val OSIS_ELEMENT_LG: String = "lg"
    const val OSIS_ELEMENT_L: String = "l"
    const val OSIS_ELEMENT_LB: String = "lb"
    const val OSIS_ELEMENT_HI: String = "hi"

    const val ATTRIBUTE_TEXT_OSISIDWORK: String = "osisIDWork"
    const val ATTRIBUTE_WORK_OSISWORK: String = "osisWork"
    const val OSIS_ATTR_OSISID: String = "osisID"
    const val OSIS_ATTR_SID: String = "sID"
    const val OSIS_ATTR_EID: String = "eID"
    const val ATTRIBUTE_W_LEMMA: String = "lemma"
    const val ATTRIBUTE_FIGURE_SRC: String = "src"
    const val ATTRIBUTE_TABLE_BORDER: String = "border"
    const val ATTRIBUTE_TABLE_ROLE: String = "role"
    const val ATTRIBUTE_CELL_ALIGN: String = "align"
    const val ATTRIBUTE_CELL_ROWS: String = "rows"
    const val ATTRIBUTE_CELL_COLS: String = "cols"
    const val OSIS_ATTR_TYPE: String = "type"
    const val OSIS_ATTR_CANONICAL: String = "canonical"
    const val OSIS_ATTR_SUBTYPE: String = "subType"
    const val OSIS_ATTR_REF: String = "osisRef"
    const val OSIS_ATTR_LEVEL: String = "level"
    const val ATTRIBUTE_SPEAKER_WHO: String = "who"
    const val ATTRIBUTE_Q_WHO: String = "who"
    const val ATTRIBUTE_W_MORPH: String = "morph"
    const val ATTRIBUTE_OSISTEXT_OSISIDWORK: String = "osisIDWork"

    // OSIS defines the lang attribute as the one from the xml namespace
    // Typical usage element.setAttribute(OSISUtil.OSIS_ATTR_LANG, lang,
    // Namespace.XML_NAMESPACE);
    const val OSIS_ATTR_LANG: String = "lang"
    const val ATTRIBUTE_DIV_BOOK: String = "book"

    /**
     * Prefix for OSIS IDs that refer to Bibles
     */
    private const val OSISID_PREFIX_BIBLE = "Bible."

    private val EXTRA_BIBLICAL_ELEMENTS: Set<String> = setOf(OSIS_ELEMENT_NOTE, OSIS_ELEMENT_TITLE, OSIS_ELEMENT_REFERENCE)
}
