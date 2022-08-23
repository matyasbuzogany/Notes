import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { map } from "rxjs/operators";
import {NoteModel} from "./notes-dashboard/note.model";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http : HttpClient) { }

  addNote(note : any) {
    return this.http.post("http://localhost:8000/addNote?title=" + note.title + "&content=" + note.content, note, {responseType: 'text'})
      .pipe(map((res:any) => {
        return res
      }))
  }

  listNotes() {
    return this.http.get("http://localhost:8000/notes", {responseType : 'text'})
      .pipe(map ((res:any) => {
        return res
      }))
  }


  listContent(title : any) {
    return this.http.get("http://localhost:8000/listNote?title=" + title, {responseType : 'text'})
}
}
