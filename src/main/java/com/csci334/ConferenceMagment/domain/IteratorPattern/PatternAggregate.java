package com.csci334.ConferenceMagment.domain.IteratorPattern;

import com.csci334.ConferenceMagment.domain.User;

import java.util.List;

public interface PatternAggregate {
    void addPattern(User user);
    void addPatterns(List<User> users);

    void removePattern(User user);

    List<User> getAllPatterns();

    PatterIterator getPatternIterator();
}
