import { Component } from '@angular/core';
import { FormGroup,FormBuilder,Validators, FormControl } from '@angular/forms';
import { VideoService } from '../video.service';
@Component({
  selector: 'app-save-video-detail',
  templateUrl: './save-video-detail.component.html',
  styleUrls: ['./save-video-detail.component.css']
})
export class SaveVideoDetailComponent {
  public videoDetailsForm !:FormGroup;
  constructor(private formBuilder: FormBuilder,private videoService:VideoService) {}
   ngOnInit(): void {
    this.videoDetailsForm=this.formBuilder.group({
      title:['',[Validators.required]],
      description:['',Validators.required],
      videoStatus:['',Validators.required],
      tags: new FormControl([]),
      

    })}
onSubmit(){
  console.log(this.videoDetailsForm.value);
  this.videoService.updateVideo(this.videoDetailsForm.value)
  .subscribe({
    next:(res)=>{
      alert("student added successfully");
      this.videoDetailsForm.reset;
    },
    error:()=>{
      alert("error while updating video");
    }
  })
}

}
