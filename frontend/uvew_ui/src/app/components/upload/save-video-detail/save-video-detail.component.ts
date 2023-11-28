import { Component, inject } from '@angular/core';
import { FormGroup,FormBuilder,Validators, FormControl } from '@angular/forms';
import { VideoService } from '../../../services/video.service';
import { ActivatedRoute, Router } from '@angular/router';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import { MatChipInputEvent} from '@angular/material/chips';
import {LiveAnnouncer} from '@angular/cdk/a11y';
import { tagsRequiredValidator } from './tags-required-validator';
import {MatSnackBar} from '@angular/material/snack-bar';
import { ThumbnailResponse } from './ThumbnailResponse';
import { VideoDto } from './VideoDetails';


@Component({
  selector: 'app-save-video-detail',
  templateUrl: './save-video-detail.component.html',
  styleUrls: ['./save-video-detail.component.css']
})
export class SaveVideoDetailComponent {
  public videoDetailsForm!: FormGroup;
  form !: FormGroup ;
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags:string[]= [];
  selectedFile: File | undefined;
  isImageSelected: boolean=false;
  videoId: string;
  videoUrl: string;
  constructor(private formBuilder: FormBuilder,private videoService:VideoService,private router: Router,private route:ActivatedRoute,private snackBar: MatSnackBar) {
    const state= (this.router.getCurrentNavigation()?.extras.state);
    this.videoId = state?state['videoId']:'';
    this.videoUrl = state?state['videoUrl']:'';
  }
  ngOnInit(): void {
    this.videoDetailsForm=this.formBuilder.group({
      title:['',[Validators.required]],
      description:['',Validators.required],
      videoStatus:['',Validators.required],
      tags:[[], [tagsRequiredValidator(this.tags)]],
      thumbnailUrl:['',Validators.required]
    })}
  announcer = inject(LiveAnnouncer);

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    // Add tag
    if (value) {
      this.tags.push(value);
      this.videoDetailsForm?.get('tags')?.patchValue(this.tags);

    }
    // Clear the input value
    event.chipInput!.clear();
  }

  remove(tag: any): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);
      this.videoDetailsForm?.get('tags')?.patchValue(this.tags);
      this.announcer.announce(`Removed ${tag}`);
    }
  }
  onFileChanged(event:any):void{
    this.isImageSelected=true;
    this.selectedFile = event.target.files[0];

  }
  onUploadThumbnail() {
    //call server api
    if(this.selectedFile && this.videoId)
    this.videoService.uploadThumbnail(this.selectedFile,this.videoId).subscribe({
      next:(res:ThumbnailResponse)=>{
        //show snack bar
        let thumbnailUrl=res.thumbnailUrl;
        this.videoDetailsForm?.get('thumbnailUrl')?.patchValue(thumbnailUrl);
        this.snackBar.open('Thubmnail Uploaded successfully', 'Ok', {
          duration: 3000
        })
      },
      error:(err)=>{
        console.error(err);
        this.snackBar.open('Some Error Occured while uploading thumbnail', 'Ok', {
          duration: 3000
        })
      }
    }
    );
    this.isImageSelected=false;
  }

  onSubmit(){
    let videoDetails:VideoDto = { ...this.videoDetailsForm.value ,
      id: this.videoId };
    this.videoService.updateVideo(videoDetails)
    .subscribe({
      next:(res)=>{

        this.snackBar.open('Video Details saved successfully', 'Ok')
        this.videoDetailsForm.reset;
      },
      error:(err)=>{
        console.error(err);
        this.snackBar.open('Some Error Occured while uploading video', 'Ok', {
          duration: 3000
        })
      }
    })


}

}
