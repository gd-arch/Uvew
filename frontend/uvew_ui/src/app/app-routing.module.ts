import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UploadVideoComponent } from './upload-video/upload-video.component';
import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';
import { SaveVideoDetailComponent } from './save-video-detail/save-video-detail.component';
const routes: Routes = [
  { path: 'upload-video', component: UploadVideoComponent },
  { path: 'save-video-detail', component: SaveVideoDetailComponent },
  { path: '',   redirectTo: '/upload-video', pathMatch: 'full' }, // redirect to `first-component`
  { path: '**', component: PagenotfoundComponent },  // Wildcard route for a 404 page
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
