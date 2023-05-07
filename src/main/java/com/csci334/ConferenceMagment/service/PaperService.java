package com.csci334.ConferenceMagment.service;

import com.csci334.ConferenceMagment.domain.File;
import com.csci334.ConferenceMagment.domain.Notification;
import com.csci334.ConferenceMagment.domain.Paper;
import com.csci334.ConferenceMagment.domain.User;
import com.csci334.ConferenceMagment.domain.exception.PaperNotFoundException;
import com.csci334.ConferenceMagment.domain.exception.userNotFoundException;
import com.csci334.ConferenceMagment.repository.NotificationRepository;
import com.csci334.ConferenceMagment.repository.PaperRepository;
import com.csci334.ConferenceMagment.repository.ScoreRepository;
import com.csci334.ConferenceMagment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
            Notification notification = new Notification();
            notification.setSender(user);
            notification.setDate(LocalDate.from(LocalDateTime.now()));
            notification.setMsg("Decision has been made");
            notification.setPaper(paper);
            notification.setReceiver(newUser);
            notification.setType("Conference Decision");
            notificationRepository.save(notification);

        }


    }
}
