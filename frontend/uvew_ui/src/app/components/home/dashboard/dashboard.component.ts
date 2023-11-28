import { Component, OnInit } from '@angular/core';
import { VideoService } from 'src/app/services/video.service';
import { Video } from '../video-card/Video';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  videos: Video[] = [];
  isLoading: boolean = false;
  currentPage: number = 0;
  itemsPerPage: number = 12;
  totalPages: number = 0;
  constructor(private videoService: VideoService) {}

  ngOnInit(): void {
    this.loadVideos();
  }

  loadVideos(): void {
    this.toggleLoading();
    this.videoService.getVideos(this.currentPage, this.itemsPerPage).subscribe({
      next: (response) => {
        this.videos = response.content;
        this.totalPages = response.totalPages;
      },
      error: (err) => console.log(err),
      complete: () => this.toggleLoading(),
    });
  }
  toggleLoading(): void {
    this.isLoading = !this.isLoading;
  }

  // this method will be called on scrolling the page
  appendData = (): void => {
    if (this.currentPage >= this.totalPages) return;
    {
      this.toggleLoading();
      this.videoService
        .getVideos(this.currentPage, this.itemsPerPage)
        .subscribe({
          next: (response) =>
            (this.videos = [...this.videos, ...response.content]),
          error: (err) => console.log(err),
          complete: () => this.toggleLoading(),
        });
    }
  };

  onScroll = (): void => {
    this.currentPage++;
    this.appendData();
  };
}
