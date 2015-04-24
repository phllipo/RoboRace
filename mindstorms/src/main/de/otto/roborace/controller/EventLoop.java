package de.otto.roborace.controller;

import java.util.ArrayList;
import java.util.List;

public class EventLoop {
	public static interface EventLoopListener {
		void process();
	}

	private List<EventLoopListener> eventLoopListenerList = new ArrayList<EventLoopListener>();
	
	public void register(EventLoopListener eventLoopListener) {
		eventLoopListenerList.add(eventLoopListener);
	}
	
	public void start() {
		new Thread(new Runnable(){
			@Override
			public void run() {
				while (true) {
					for (EventLoopListener eventLoopListener: eventLoopListenerList) {
						eventLoopListener.process();						
					}
				}
			}			
		}
		).start();;
	}
	
}
