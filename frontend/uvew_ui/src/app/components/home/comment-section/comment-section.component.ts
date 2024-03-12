import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommentService } from 'src/app/services/comment.service';
import CommentDto from './CommentDto';
import { UserService } from 'src/app/services/user.service';
import CommentData from './CommentData';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-comment-section',
  templateUrl: './comment-section.component.html',
  styleUrls: ['./comment-section.component.css']
})
export class CommentSectionComponent implements OnInit {
  public commentForm!: FormGroup;
  @Input() videoId!: string;
  public comments:CommentDto[]=[];
  isLoading: boolean = false;
  currentPage: number = 0;
  itemsPerPage: number = 12;
  totalPages: number = 0;
  isLastPage: boolean =false;
  constructor(private fb: FormBuilder,private commentService:CommentService,private userService:UserService,private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.commentForm = this.fb.group({
      comment: ['', [Validators.required]],
    });
  }
  ngOnChanges(changes: SimpleChanges) {
    if (changes['videoId']) {
      this.loadComments();
    }
  }

  onSubmit() {
    // Handle form submission here
    if (this.commentForm?.valid) {
      const commentValue = this.commentForm?.value?.comment;
      // request backend to add the comment
      let commentData :CommentData = {
        commentId:"",
        videoId:this.videoId,
        text:commentValue
      }
      this.addComment(commentData);
    
    }
  }
  
  loadComments(): void {
    if (this.videoId) {
      this.commentService.getCommentsForVideo(this.videoId,this.currentPage,this.itemsPerPage).subscribe(
        {
          next:(commentDto) => {
            this.comments = [...this.comments, ...commentDto.content]
            this.totalPages=commentDto.totalPages;
            this.isLastPage = commentDto.lastPage;
            console.log(commentDto);

          },
          error:(err)=>{console.log(err)}}
      )
    }
  }
  addComment(commentData:CommentData):void{
    if(commentData.videoId){
      this.commentService.postComment(commentData)
      .subscribe({
        next:(res)=>{
          this.snackBar.open('Comment added successfully', 'Ok')
          this.commentForm.reset();
          this.loadComments();

        },
        error:(err)=>{
          console.error(err);
          this.snackBar.open('Some Error Occured while adding comment', 'Ok', {
            duration: 3000
          })
        }
      })
    }
  }

  loadMoreComments(){
    this.currentPage = this.currentPage+1;
    this.loadComments();

  }
 
}
