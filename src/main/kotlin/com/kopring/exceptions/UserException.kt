package com.kopring.exceptions

open class UserException : RuntimeException()
class DuplUsernameException: UserException()
class UserNotFoundException: UserException()
class NotOwnerException : UserException()