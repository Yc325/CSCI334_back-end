package com.csci334.ConferenceMagment.domain.IteratorPattern;

import com.csci334.ConferenceMagment.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PatternAggregateImlp implements PatternAggregate{
    List patternList;

    public PatternAggregateImlp() {
        this.patternList = new ArrayList<>();
    }

    @Override
    public void addPattern(User user){
        patternList.add(user);
    }

    @Override
    public void addPatterns(List<User> users){
        patternList = new ArrayList<>(users);
    }

    @Override
    public void removePattern(User user){
        patternList.remove(user);
    }

    @Override
    public List<User> getAllPatterns(){
        return patternList;
    }

    @Override
    public PatterIterator getPatternIterator(){
        return new PatternIteratorImpl(patternList);
    }
}
