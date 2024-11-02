package com.kopring.exceptions

sealed class PostException:RuntimeException()

class NotFoundPostException:PostException()
class NotFoundCommentException:PostException()