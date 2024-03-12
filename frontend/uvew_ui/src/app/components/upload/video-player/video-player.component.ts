import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.css']
})
export class VideoPlayerComponent implements OnChanges {
  @Input() videoUrl:string|undefined ;
  isVideoUrlAvailable: boolean = false;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['videoUrl'] && changes['videoUrl'].currentValue) {
      this.isVideoUrlAvailable=true;
      const newValue = changes['videoUrl'].currentValue;
      const previousValue = changes['videoUrl'].previousValue;
      console.log(`Input value changed from ${previousValue} to ${newValue}`);
    }  }

}

