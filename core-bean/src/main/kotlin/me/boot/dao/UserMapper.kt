package me.boot.dao

interface UserMapper {

    fun createUser(name: String?): Int
}