import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UploadVideoComponent } from './components/upload/upload-video/upload-video.component';
import { PagenotfoundComponent } from './components/pagenotfound/pagenotfound.component';
import { SaveVideoDetailComponent } from './components/upload/save-video-detail/save-video-detail.component';
import { DashboardComponent } from './components/home/dashboard/dashboard.component';
import { VideoDetailComponent } from './components/home/video-detail/video-detail.component';
const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'upload-video', component: UploadVideoComponent },
  { path: 'save-video-detail', component: SaveVideoDetailComponent },
  { path: 'video-details/:videoId', component: VideoDetailComponent },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' }, // redirect to `first-component`
  { path: '**', component: PagenotfoundComponent }, // Wildcard route for a 404 page
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
