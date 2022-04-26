package com.pedro_bruno.githublistapp.domain.exceptions

import java.lang.Exception

open class RequestException() : Exception()

class LimitResquestException : RequestException()
class GenericRequestException() : RequestException()
class ValidationFailedException : RequestException()