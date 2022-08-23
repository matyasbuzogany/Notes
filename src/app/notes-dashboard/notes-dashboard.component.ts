import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {NoteModel} from "./note.model";
import {ApiService} from "../api.service";

@Component({
  selector: 'app-notes-dashboard',
  templateUrl: './notes-dashboard.component.html',
  styleUrls: ['./notes-dashboard.component.css']
})
export class NotesDashboardComponent implements OnInit {

  formNotes !: FormGroup;
  noteModel : NoteModel = new NoteModel();
  noteData !: any;
  showAddButton !: boolean;
  showUpdateButton !: boolean;
  constructor(private formBuilder : FormBuilder, private apiService : ApiService) { }

  ngOnInit(): void {
    this.formNotes = this.formBuilder.group({
      title : [''],
      content : ['']
    })
    this.listAllNotes()
  }

  addNote() {
    console.log(this.formNotes.value.titleInput)
    this.noteModel.title = this.formNotes.value.title;
    this.noteModel.content = this.formNotes.value.content;

    this.apiService.addNote(this.noteModel).subscribe( res => {
      console.log(res);
      alert("Note added successfully!")
    });
    this.listAllNotes()
  }

  clickAddNote() {
    this.formNotes.reset();
    this.showAddButton = true;
    this.showUpdateButton = false;
  }

  listAllNotes() {
    this.apiService.listNotes().subscribe( res => {
      console.log(JSON.stringify(res))
      var split = res.split("\n")
      split.pop();
      this.noteData = split;
    })
  }

  listContent(row : any) {
    this.formNotes.reset();
    this.showAddButton = false;
    this.showUpdateButton = true;
    this.noteModel.id = row.id;

    this.apiService.listContent(row).subscribe( res => {
      this.formNotes.controls['title'].setValue(row);
      this.formNotes.controls['content'].setValue(res);
    })
  }


  clickEdit() {
    this.formNotes.reset();
    this.showAddButton = false;
    this.showUpdateButton = true;
  }
}
