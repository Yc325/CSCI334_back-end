package com.csci334.ConferenceMagment.service;

import com.csci334.ConferenceMagment.domain.File;
import com.csci334.ConferenceMagment.domain.IteratorPattern.PatterIterator;
import com.csci334.ConferenceMagment.domain.IteratorPattern.PatternAggregate;
import com.csci334.ConferenceMagment.domain.IteratorPattern.PatternAggregateImlp;
import com.csci334.ConferenceMagment.domain.builderPattern.*;
import com.csci334.ConferenceMagment.domain.Paper;
import com.csci334.ConferenceMagment.domain.User;
import com.csci334.ConferenceMagment.domain.exception.PaperNotFoundException;
import com.csci334.ConferenceMagment.domain.exception.userNotFoundException;
import com.csci334.ConferenceMagment.domain.observer.EmailDecision;
import com.csci334.ConferenceMagment.domain.observer.Observer;
import com.csci334.ConferenceMagment.repository.NotificationRepository;
import com.csci334.ConferenceMagment.repository.PaperRepository;
import com.csci334.ConferenceMagment.repository.ScoreRepository;
import com.csci334.ConferenceMagment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileService fileService;

    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    private JavaMailSender mailSender;

    private List<Observer> obs = new ArrayList<>();

    public void inform(){
        for (Observer ob: obs){
            mailSender.send(ob.sendNotifcation());
        }
    }
    public void addObserver(Observer observer){
        obs.add(observer);
    }

    public Paper save(User user) {
        User author = userRepository.findByUsername(user.getUsername()).orElseThrow(()-> new userNotFoundException(user.getUsername()));
        Paper paper = new Paper();
//        File file = new File();
//        paper.setFile(file);
        paper.setStatus("Needs to be submitted");
        paper.addAuthor(author);
        return paperRepository.save(paper);
    }
    public Paper addAuthorToPaper(Long paperId,String username){
        User author = userRepository.findByUsername(username).orElseThrow(()-> new userNotFoundException(username));
        Paper paper = getPaper(paperId);
        paper.addAuthor(author);
        return paperRepository.save(paper);
    }

    public Paper addReviewerToPaper(Long paperId,String username){
        User reviewer = userRepository.findByUsername(username).orElseThrow(()-> new userNotFoundException(username));
        Paper paper = getPaper(paperId);
        paper.addReviewer(reviewer);
        return paperRepository.save(paper);
    }
    public Paper authoAddReviwerTopaper(Paper paper, User user){
        paper.addReviewer(user);
        return paperRepository.save(paper);
    }

    public Set<Paper> findByAuthors(User user){
        return paperRepository.findByAuthors(user);
    }

    public Set<Paper> findByReviewers(User user){
        return paperRepository.findByReviewers(user);
    }
    public Boolean findByReviewersBool(User user, Long paperID) {
        Set<Paper> AssginedPapers = paperRepository.findByReviewers(user);
        for (Paper p :AssginedPapers){
            if(p.getId()==paperID) {
                return true;
            }
        }
        return false;
    }




    public Optional<Paper> findById(Long paperId) {
        return paperRepository.findById(paperId);
    }

    public Paper getPaper(Long paperId) {
        return paperRepository.findById(paperId).orElseThrow(()-> new PaperNotFoundException(paperId));
    }
    @Transactional
    public Paper addFile(Long paperId, MultipartFile file) {
        Paper paper = getPaper(paperId);
        File fileName = fileService.storeFile(file);
        paper.setFile(fileName);
        paper.setName(fileName.getFileName().split("\\.")[0]);
        paper.setStatus("Submitted");
        return paper;
    }

    public List<Paper> getAllPapers(){
        return paperRepository.findAll();
    }

    public Set<User> getAllReviwers(){
        return userRepository.findByReviwer();
    }

    public Set<Paper> getAllSubmittedPapers(){
        return paperRepository.findSubmittedPapers();
    }

    public void makeDecision(Long paperId, Boolean decision, User user) {
        Paper paper = getPaper(paperId);
        paper.setConferenceManagementDecision(decision);
        paperRepository.save(paper);

        // Send notification

        List<User> authors = paper.getAuthors();
        int sizeAuthors = authors.size();

        for(int i = 0; i<sizeAuthors; i++){
            User newUser = authors.get(i);
            BuildNotification decisionNotification = new DecisionNotification(newUser,paper,user);
            ConstractorNotification notificationComment = new ConstractorNotification(decisionNotification);
            Notification notification = notificationComment.constructNotification();
            notificationRepository.save(notification);
            addObserver(new EmailDecision(
                    newUser.getUsername(),
                    paper.getId(),
                    notification.getType(),
                    newUser.getEmail(),
                    user.getUsername()));
        }
        inform();
    }


    public void bidPaper(Long paperId, User user) {
        Paper paper = getPaper(paperId);

        PatternAggregate listAllReviwers = new PatternAggregateImlp();
        listAllReviwers.addPatterns(new ArrayList<>(getAllReviwers()));

        PatternAggregate AssignedReviwers = new PatternAggregateImlp();
        AssignedReviwers.addPatterns(paper.getReviewers());

        PatterIterator patterIteratorListAllReviewers = listAllReviwers.getPatternIterator();

        while(!patterIteratorListAllReviewers.isLastPattern()){
            User reviewer = patterIteratorListAllReviewers.nextPattern();
            if(reviewer.getId() == user.getId()){
                listAllReviwers.removePattern(reviewer);
                AssignedReviwers.removePattern(reviewer);
                break;
            }
        }
        PatterIterator patterIteratorListAllReviewers1 = listAllReviwers.getPatternIterator();
        User addedUser = patterIteratorListAllReviewers1.getRandomUser();
        AssignedReviwers.addPattern(addedUser);
        paper.setReviewers(AssignedReviwers.getAllPatterns());
        paperRepository.save(paper);

    }


}
