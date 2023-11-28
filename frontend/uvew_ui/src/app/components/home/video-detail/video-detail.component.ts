import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from 'src/app/services/video.service';
import { Video } from '../video-card/Video';
import { VideoPageDto } from 'src/app/models/Page';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrls: ['./video-detail.component.css'],
})
export class VideoDetailComponent implements OnInit {
  public videoId: string;
  public video: Video | undefined;
  public videoAvailable: any = false;
  public showUnSubscribeButton: any = true;
  public showSubscribeButton: any;
  constructor(
    private activatedRoute: ActivatedRoute,
    private videoService: VideoService,
    private snackBar: MatSnackBar
  ) {
    this.videoId = this.activatedRoute.snapshot.params['videoId'];
    
  }
  ngOnInit(): void {
    this.videoService.getVideo(this.videoId).subscribe({
      next:(data: Video) =>{ 
        this.video = data;
        this.videoAvailable=true;
      },
      error:(err)=>{
        this.snackBar.open('Some Error Occured while getting video', 'Ok', {
          duration: 3000
        })
      }
  });  
  }
  unSubscribeToUser() {
    throw new Error('Method not implemented.');
  }
  subscribeToUser() {
    throw new Error('Method not implemented.');
  }

  disLikeVideo() {
    throw new Error('Method not implemented.');
  }
  likeVideo() {
    throw new Error('Method not implemented.');
  }
}
