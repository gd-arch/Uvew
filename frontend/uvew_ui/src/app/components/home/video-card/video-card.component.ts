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
  convertToTimeElaspsed(date:string):string{
    const localDateTime = new Date(date); 
    const currentDate = new Date(); 
    
    // Calculate time difference
    const timeDifference = currentDate.getTime() - localDateTime.getTime();
    
    // Calculate elapsed time in days, months, and years
    const elapsedDays = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
    const elapsedMonths = Math.floor(elapsedDays / 30.436875); // Average days in a month
    const elapsedYears = Math.floor(elapsedMonths / 12);
    if(elapsedYears >= 1 ){
      return elapsedYears+" year ago";
    }else if(elapsedMonths >= 1){
      return elapsedMonths+" month ago";
    }else {
      return elapsedDays+ " days ago" ;
    }

    
  }

  

}
