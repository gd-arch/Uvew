import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UploadVideoComponent } from './upload-video/upload-video.component';
import { HeaderComponent } from './header/header.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { NgxFileDropModule } from 'ngx-file-drop';
import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';
import { HttpClientModule } from '@angular/common/http';
import { SaveVideoDetailComponent } from './save-video-detail/save-video-detail.component';
import { BodyComponent } from './body/body.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatChipsModule } from '@angular/material/chips';
import { TagsComponent } from './tags/tags.component';
import { FormControlPipe } from './pipes/custom-pipe/form-control.pipe';
import {MatProgressBarModule} from '@angular/material/progress-bar';

@NgModule({
    declarations: [
        AppComponent,
        UploadVideoComponent,
        HeaderComponent,
        SidebarComponent,
        PagenotfoundComponent,
        SaveVideoDetailComponent,
        BodyComponent,
        FormControlPipe,
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
        TagsComponent,
        MatProgressBarModule
        
    ]
})
export class AppModule { }
