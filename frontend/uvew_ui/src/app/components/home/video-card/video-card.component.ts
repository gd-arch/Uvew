import { Component, Input, OnInit } from '@angular/core';
import { Video } from './Video';

@Component({
  selector: 'app-video-card',
  templateUrl: './video-card.component.html',
  styleUrls: ['./video-card.component.css']
})
export class VideoCardComponent implements OnInit{ 
  @Input() video!:Video ;
  public thumbnailSrc: string='';
  public uploadedBySrc:string='';
  public defaultImg: string = '/assets/images/Placeholder.webp';
  public defaultUploadedBy: string ='/assets/images/user.jpg';

  ngOnInit(): void {
    console.log(this.video);
    if(this.video.thumbnailUrl!=null)
      this.thumbnailSrc=this.video.thumbnailUrl;
    else this.onError('thumbnail');

  }
  public onError(err:string) {
    switch(err){
      case 'thumbnail':
        this.thumbnailSrc = this.defaultImg;
        break;
      case 'uploadedBy':
        this.uploadedBySrc = this.defaultUploadedBy;

    }
  }

  

}
