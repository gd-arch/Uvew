import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UploadVideoComponent } from './components/upload/upload-video/upload-video.component';
import { HeaderComponent } from './components/header/header.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NgxFileDropModule } from 'ngx-file-drop';
import { PagenotfoundComponent } from './components/pagenotfound/pagenotfound.component';
import { HttpClientModule } from '@angular/common/http';
import { SaveVideoDetailComponent } from './components/upload/save-video-detail/save-video-detail.component';
import { BodyComponent } from './components/body/body.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { VideoPlayerComponent } from './components/upload/video-player/video-player.component';
import { VgCoreModule } from '@videogular/ngx-videogular/core';
import { VgControlsModule } from '@videogular/ngx-videogular/controls';
import { VgOverlayPlayModule } from '@videogular/ngx-videogular/overlay-play';
import { VgBufferingModule } from '@videogular/ngx-videogular/buffering';
import { DashboardComponent } from './components/home/dashboard/dashboard.component';
import { VideoCardComponent } from './components/home/video-card/video-card.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { VideoDetailComponent } from './components/home/video-detail/video-detail.component';
import { RouterModule } from '@angular/router';
import { CommentSectionComponent } from './components/home/comment-section/comment-section.component';
import { VideoElementComponent } from './components/home/video-element/video-element.component';


@NgModule({
    declarations: [
      AppComponent,
      UploadVideoComponent,
      HeaderComponent,
      SidebarComponent,
      PagenotfoundComponent,
      SaveVideoDetailComponent,
      BodyComponent,
      VideoPlayerComponent,
      DashboardComponent,
      VideoCardComponent,
      VideoDetailComponent,
      CommentSectionComponent,
      VideoElementComponent,
    ],
    providers: [],
    bootstrap: [AppComponent],
    imports: [
      BrowserModule,
      AppRoutingModule,
      BrowserAnimationsModule,
      MatButtonModule,
      MatIconModule,
      MatChipsModule,
      MatToolbarModule,
      NgxFileDropModule,
      HttpClientModule,
      ReactiveFormsModule,
      MatProgressBarModule,
      MatFormFieldModule,
      MatSnackBarModule,
      VgCoreModule,
      VgControlsModule,
      VgOverlayPlayModule,
      VgBufferingModule,
      InfiniteScrollModule,
      RouterModule,
    ]
})
export class AppModule {}
