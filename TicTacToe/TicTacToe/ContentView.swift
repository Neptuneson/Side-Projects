//
//  ContentView.swift
//  TicTacToe
//
//  Created by Robert Petro on 11/4/21.
//

import SwiftUI

struct ContentView: View {
    
    @State var isGameShowing = false
    @State var isSettingsShowing = false
    @State var diff = 1
    
    var body: some View {
        if isGameShowing {
            MainGameView(showingGame: $isGameShowing, diff: $diff)
        } else if isSettingsShowing {
            SettingsView(showingSettings: $isSettingsShowing)
        } else {
            HomeScreenView(showingSettings: $isSettingsShowing, showingGame: $isGameShowing, diff: $diff)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
