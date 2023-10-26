import { Component } from '@angular/core';
import { FormGroup,FormBuilder,Validators, FormControl } from '@angular/forms';
import { VideoService } from '../video.service';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-save-video-detail',
  templateUrl: './save-video-detail.component.html',
  styleUrls: ['./save-video-detail.component.css']
})
export class SaveVideoDetailComponent {
  public videoDetailsForm !:FormGroup;
  constructor(private formBuilder: FormBuilder,private videoService:VideoService,private activateRoute:ActivatedRoute) {}
   ngOnInit(): void {
    this.videoDetailsForm=this.formBuilder.group({
      title:['',[Validators.required]],
      description:['',Validators.required],
      videoStatus:['',Validators.required],
      tags: new FormControl([]),
      

    })}
onSubmit(){
  console.log(this.videoDetailsForm.value);
  let videoDetails = this.videoDetailsForm.value;
  videoDetails.id = 
  this.videoService.updateVideo(this.videoDetailsForm.value)
  .subscribe({
    next:(res)=>{
      alert("Video Details added successfully");
      this.videoDetailsForm.reset;
    },
    error:()=>{
      alert("error while updating video details");
    }
  })
}

}
