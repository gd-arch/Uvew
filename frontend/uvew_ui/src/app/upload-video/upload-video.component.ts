import { HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgxFileDropEntry, FileSystemFileEntry, FileSystemDirectoryEntry } from 'ngx-file-drop';
import { VideoService } from '../video.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-upload-video',
  templateUrl: './upload-video.component.html',
  styleUrls: ['./upload-video.component.css']
})
export class UploadVideoComponent {
  constructor(private videoService: VideoService, private router: Router){}
  public files: NgxFileDropEntry[] = [];
  private fileUploaded:boolean=false;
  private file:File | undefined;

  dropped(files: NgxFileDropEntry[]) {
    this.files = files;
    for (const droppedFile of files) {
      // Is it a file?
      if (droppedFile.fileEntry.isFile) {
        const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        fileEntry.file((file: File) => {
         this.fileUploaded =true;
         this.file=file;
        });
      } else {
        // It was a directory (empty directories are added, otherwise only files)
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
        this.fileUploaded =false;
      }
    }
  }
  uploadVideo(){
    if(this.file!=undefined){
      if(this.fileUploaded)
        this.videoService.uploadVideo(this.file).subscribe(
          {
            next: (res: any) => {
              this.router.navigateByUrl("/save-video-detail/"+res.id);
          },
          error: (err: any) => { 
            alert("error while Uploading the files"); 
          }
        }
      );

    }
  }

}
