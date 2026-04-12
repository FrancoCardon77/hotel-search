package com.riu.hotelsearch.application.ports;

import com.riu.hotelsearch.domain.model.SearchCount;

public interface CountUseCase {

    SearchCount count(String searchId);
}
