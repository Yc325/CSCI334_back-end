package com.csci334.ConferenceMagment.domain.IteratorPattern;

import com.csci334.ConferenceMagment.domain.User;

import java.util.List;
import java.util.Random;

public class PatternIteratorImpl implements PatterIterator{
    public List patternList;
    int position;

    User user;

    public PatternIteratorImpl(List patternList) {
        this.patternList = patternList;
    }

    @Override
    public User nextPattern(){
        user = (User)patternList.get(position);
        position++;
        return user;
    }

    @Override
    public User getRandomUser(){
        Random rand = new Random();
        user = (User)patternList.get(rand.nextInt(patternList.size()));
        return user;
    }

    @Override
    public boolean isLastPattern(){
        if(position<patternList.size()){
            return false;
        }
        return true;
    }
}
