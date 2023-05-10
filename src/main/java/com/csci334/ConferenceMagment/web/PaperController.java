package com.csci334.ConferenceMagment.web;

import com.csci334.ConferenceMagment.domain.File;
import com.csci334.ConferenceMagment.domain.Paper;
import com.csci334.ConferenceMagment.domain.User;
import com.csci334.ConferenceMagment.payload.Response;
import com.csci334.ConferenceMagment.service.FileService;
import com.csci334.ConferenceMagment.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/papers")
public class PaperController {
    @Autowired
    private PaperService paperService;

    @Autowired
    private FileService fileService;


    ///REST REQ POST
    @PostMapping("")
    public ResponseEntity<?> createPaper(@AuthenticationPrincipal User user){
        Paper newPaper = paperService.save(user);
        return ResponseEntity.ok(newPaper);
    }

    @PutMapping("{paperId}")
    public ResponseEntity<?> makeDecision(@PathVariable Long paperId, @RequestParam Boolean decision,@AuthenticationPrincipal User user){
        paperService.makeDecision(paperId,decision,user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("")
    public ResponseEntity<?> bidPaper(@RequestParam Long paperId, @AuthenticationPrincipal User user){
        paperService.bidPaper(paperId,user);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("{paperId}")
    public ResponseEntity<?> uploadFileToPaper(@PathVariable Long paperId, @RequestParam MultipartFile file, @AuthenticationPrincipal User user){
        Paper paper  = paperService.addFile(paperId,file);
        return ResponseEntity.ok(paper);
    }

    @PostMapping("{paperid}/addauthor/{username}")
    public ResponseEntity<?> addAuthor(@PathVariable Long paperid, @PathVariable String username, @AuthenticationPrincipal User user) {
        Paper paper = paperService.addAuthorToPaper(paperid,username);
        return ResponseEntity.ok(paper);
    }

    @PostMapping("{paperid}/addreviewer/{username}")
    public ResponseEntity<?> addReviewer(@PathVariable Long paperid, @PathVariable String username, @AuthenticationPrincipal User user){
        Paper paper = paperService.addReviewerToPaper(paperid,username);
        return ResponseEntity.ok(paper);
    }

    @PostMapping("/uploadFile")
    public Response uploadFile(@RequestParam MultipartFile file){
        File fileName = fileService.storeFile(file);

        String fileDowloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/dowloadFile/")
                .path(fileName.getFileName())
                .toUriString();
        return new Response(fileName.getFileName(), fileDowloadUri,file.getContentType(), file.getSize());
    }

    ///REST REQ GET
    @GetMapping("")
    public ResponseEntity<?> getPapers(@AuthenticationPrincipal User user){
       return ResponseEntity.ok(paperService.findByAuthors(user));
    }

    @GetMapping("reviewer")
    public ResponseEntity<?> getPapersReviewer(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(paperService.findByReviewers(user));
    }

    @GetMapping("{paperId}")
    public ResponseEntity<?> getPaper(@PathVariable Long paperId, @AuthenticationPrincipal User user){
        Optional<Paper> paper = paperService.findById(paperId);
        return ResponseEntity.ok(paper.orElse(new Paper()));
    }

    @GetMapping("/dowloadFile/{fileId:.+}")
    public ResponseEntity<byte[]> dowloadFile(@PathVariable String fileId){

        File file = fileService.getFile(fileId);

        HttpHeaders header = new HttpHeaders();

        header.setContentType(MediaType.valueOf(file.getFileType()));
        header.setContentLength(file.getData().length);
        header.set("Content-Disposition", "attachment; filename=" + file.getFileName());
        return new ResponseEntity<>(file.getData(), header, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPapers(@AuthenticationPrincipal User user){
        List<Paper> paperList = paperService.getAllPapers();
        return ResponseEntity.ok(paperList);
    }

    @GetMapping("/reviwers")
    public ResponseEntity<?> getAllAuthorityNumber(){

        //Get all Reviewers
        Set<User> listReviewers = paperService.getAllReviwers();
        //get size of Reviewers
        int sizeReviewers = listReviewers.size(); //current number is 4

        //Get all submitted papers
        Set<Paper> listSubmittedPapers = paperService.getAllSubmittedPapers(); //current number is 4
        //get size of papers
        int sizePapers = listSubmittedPapers.size();


        //Equal division of paper per author
        int equalDivisionPerPaper = sizePapers/2; //should be 2
        //Equal division of paper per author
        int equalDivisionPerReviewer = sizeReviewers/2; //should be 2

        int counterAssigendPapers = 0;

        List<User> users = new ArrayList<User>(listReviewers);
        List<Paper> papers = new ArrayList<Paper>(listSubmittedPapers);


        //Assigning Reviewers
        for(int i = 0; i<sizeReviewers; i++){
            //Set Users
            User user = users.get(i);

            //That's a counter that counts how many papers were assigned
            int counter_paper = 0;

            if (i >= equalDivisionPerReviewer){
                counterAssigendPapers = equalDivisionPerPaper;
            }

            //Assigning Papers
            for (int j = counterAssigendPapers; j < sizePapers; j++){
                counter_paper +=1;

                Paper paper = papers.get(j);


                //assign paper to reviewer
                paperService.authoAddReviwerTopaper(paper,user);

                if (counter_paper == equalDivisionPerPaper){
                    break;
                }
            }
        }
        return ResponseEntity.ok(equalDivisionPerReviewer);
    }

}
