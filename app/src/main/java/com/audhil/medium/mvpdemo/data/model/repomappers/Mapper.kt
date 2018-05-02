package com.audhil.medium.mvpdemo.data.model.repomappers

/*
 * Created by mohammed-2284 on 11/04/18.
 */

interface Mapper<out OUT, in IN> {
    fun map(input: IN, tag: String): OUT
}