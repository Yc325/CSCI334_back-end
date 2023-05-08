package com.csci334.ConferenceMagment.domain.IteratorPattern;

import com.csci334.ConferenceMagment.domain.User;

public interface PatterIterator {
    User nextPattern();
    User getRandomUser();
    boolean isLastPattern();
}
