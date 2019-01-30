
package com.ingic.lmslawyer.entities.event_entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventEnt {

    @SerializedName("events")
    @Expose
    private ArrayList<Event> events = null;

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

}
