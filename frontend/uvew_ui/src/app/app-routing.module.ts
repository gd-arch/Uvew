import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PagenotfoundComponent } from './components/pagenotfound/pagenotfound.component';
import { SaveVideoDetailComponent } from './components/upload/save-video-detail/save-video-detail.component';
import { DashboardComponent } from './components/home/dashboard/dashboard.component';
import { VideoDetailComponent } from './components/home/video-detail/video-detail.component';
import { UploadVideoComponent } from './components/upload/upload-video/upload-video.component';
import { AccessDeniedComponent } from './access-denied/access-denied.component';
import { AuthGuard } from './auth.guard';
import { CallbackComponent } from './components/util/callback/callback.component';
const routes: Routes = [
  { path: 'callback', component: CallbackComponent},
  { path: 'dashboard', component: DashboardComponent ,canActivate: [AuthGuard]},
  { path: 'upload-video', component: UploadVideoComponent ,canActivate: [AuthGuard]},
  { path: 'save-video-detail', component: SaveVideoDetailComponent ,canActivate: [AuthGuard]},
  { path: 'video-details/:videoId', component: VideoDetailComponent,canActivate: [AuthGuard] },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' }, 
  { path: 'access-denied',component: AccessDeniedComponent,},
  { path: '**', component: PagenotfoundComponent }, // Wildcard route for a 404 page
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
