import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from 'src/app/services/video.service';
import { Video } from '../video-card/Video';
import { VideoPageDto } from 'src/app/models/VideoPageDto';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrls: ['./video-detail.component.css'],
})
export class VideoDetailComponent implements OnInit {
  public videoId: string;
  public video: Video = {
    id: '',
    title: '',
    userSubId: '',
    authorName: '',
    likes: 0,
    dislikes: 0,
    tags: [],
    videoStatus: 'PUBLIC',
    videoCount: 0,
    thumbnailUrl: '',
    videoUrl: '',
    commentList: [],
    description: '',
    views: 0,
    dateCreated: '',
  };
  public videoAvailable: any = false;
  public showUnSubscribeButton: any = true;
  public showSubscribeButton: any;
  public totalSubscribers: number = 0;
  public isSubscribed: boolean = false;
  constructor(
    private activatedRoute: ActivatedRoute,
    private videoService: VideoService,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) {
    this.videoId = this.activatedRoute.snapshot.params['videoId'];
  }
  ngOnInit(): void {
    this.viewVideo();
    this.loadVideo();
  }
  loadVideo() {
    if (this.videoId) {
      this.videoService.getVideo(this.videoId).subscribe({
        next: (data: Video) => {
          this.video = data;
          this.videoAvailable = true;
          this.getTotalSubscribers(this.video.userSubId);
        },
        error: (err) => {
          console.log(err);
          this.snackBar.open('Some Error Occured while getting video', 'Ok', {
            duration: 3000,
          });
        },
      });
    }
  }
  handleButtonClick(userSubId: string) {
    if (this.isSubscribed) {
      this.unSubscribeToUser(userSubId);
    } else {
      this.subscribeToUser(userSubId);
    }
  }
  unSubscribeToUser(userSubId: string) {
    if (userSubId) {
      this.userService.unsubscribeFromUser(userSubId).subscribe({
        next: (response) => {
          console.log(response);
          this.loadVideo();
          this.isSubscribed = false;
        },
        error: (err) => {
          console.log(err);
          this.snackBar.open(
            'Some Error Occured while unsubscribing to channel',
            'Ok',
            {
              duration: 3000,
            }
          );
        },
      });
    }
  }
  subscribeToUser(userSubId: string) {
    if (userSubId) {
      this.userService.subscribeToUser(userSubId).subscribe({
        next: (response) => {
          console.log(response);
          this.loadVideo();
          this.isSubscribed = true;
        },
        error: (err) => {
          console.log(err);
          this.snackBar.open(
            'Some Error Occured while subscribing to channel',
            'Ok',
            {
              duration: 3000,
            }
          );
        },
      });
    }
  }

  disLikeVideo() {
    if (this.videoId) {
      this.videoService.dislikeVideo(this.videoId).subscribe({
        next: (response) => {
          console.log(response);
          this.loadVideo();
        },
        error: (err) => {
          console.log(err);
          this.snackBar.open('Some Error Occured while disliking video', 'Ok', {
            duration: 3000,
          });
        },
      });
    }
  }
  likeVideo() {
    if (this.videoId) {
      this.videoService.likeVideo(this.videoId).subscribe({
        next: (response) => {
          console.log(response);
          this.loadVideo();
        },
        error: (err) => {
          console.log(err);
          this.snackBar.open('Some Error Occured while liking video', 'Ok', {
            duration: 3000,
          });
        },
      });
    }
  }
  viewVideo(){
    if (this.videoId) {
      this.videoService.viewVideo(this.videoId).subscribe({
        next: (response) => {
          console.log(response);
        },
        error: (err) => {
          console.log(err);
          this.snackBar.open('Some Error Occured while updating views of video', 'Ok', {
            duration: 3000,
          });
        },
      });
    }
  }
  getTotalSubscribers(userSubId: string) {
    if (userSubId) {
      // this.userService.getTotalSubscribers(userSubId).subscribe(
      //   (response:any)=>{
      //     this.totalSubscribers = response.totalSubscribers;
      //   }
      // );
    }
  }
  
  convertToTimeElaspsed(date: string): string {
    const localDateTime = new Date(date);
    const currentDate = new Date();

    // Calculate time difference
    const timeDifference = currentDate.getTime() - localDateTime.getTime();

    // Calculate elapsed time in days, months, and years
    const elapsedDays = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
    const elapsedMonths = Math.floor(elapsedDays / 30.436875); // Average days in a month
    const elapsedYears = Math.floor(elapsedMonths / 12);
    if (elapsedYears >= 1) {
      return elapsedYears + ' year ago';
    } else if (elapsedMonths >= 1) {
      return elapsedMonths + ' month ago';
    } else {
      return elapsedDays + ' days ago';
    }
  }
}
