package org.crosswire.ksword.book.sword.state

import org.crosswire.ksword.versification.Testament

data class LastLoadedBlock(val testament: Testament, val blockNum: Int, val uncompressed: ByteArray)
