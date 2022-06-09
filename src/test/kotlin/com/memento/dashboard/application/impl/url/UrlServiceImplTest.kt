package com.memento.dashboard.application.impl.url

import com.memento.dashboard.domain.url.UrlDomainService
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
internal class UrlServiceImplTest {

    @Mock
    lateinit var urlDomainService: UrlDomainService

    @InjectMocks
    lateinit var urlServiceImpl: UrlServiceImpl
}