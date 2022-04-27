package com.pedro_bruno.githublistapp.domain.exceptions

import java.lang.Exception

open class UseCaseException() : Exception()

class EmptySearchException : UseCaseException()
class PageIsZeroException : UseCaseException()