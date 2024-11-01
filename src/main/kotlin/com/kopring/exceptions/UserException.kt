package com.kopring.exceptions

sealed class UserException : RuntimeException()
class DuplUsernameException : UserException()
class UserNotFoundException : UserException()
class NotOwnerException : UserException()
class UnknowUserException : UserException()