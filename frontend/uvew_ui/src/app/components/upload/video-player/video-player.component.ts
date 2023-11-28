import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.css']
})
export class VideoPlayerComponent implements OnChanges {
  @Input() videoUrl:string|undefined;
isVideoUrlAvailable: any;
  constructor(){
    console.log(this.videoUrl);
    //this is not getting correctly printed because parent compoent sends url after init 
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['videoUrl']) {
      const newValue = changes['videoUrl'].currentValue;
      const previousValue = changes['videoUrl'].previousValue;

      console.log(`Input value changed from ${previousValue} to ${newValue}`);
    }  }

}

