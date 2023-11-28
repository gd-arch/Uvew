import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VideoElementComponent } from './video-element.component';

describe('VideoElementComponent', () => {
  let component: VideoElementComponent;
  let fixture: ComponentFixture<VideoElementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VideoElementComponent]
    });
    fixture = TestBed.createComponent(VideoElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
