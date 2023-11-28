import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-comment-section',
  templateUrl: './comment-section.component.html',
  styleUrls: ['./comment-section.component.css']
})
export class CommentSectionComponent implements OnInit {
  public commentForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.commentForm = this.fb.group({
      comment: ['', [Validators.required]],
    });
  }

  onSubmit() {
    // Handle form submission here
    if (this.commentForm?.valid) {
      const commentValue = this.commentForm?.value?.comment;
      // Perform actions with the comment value

    }
  }
}
