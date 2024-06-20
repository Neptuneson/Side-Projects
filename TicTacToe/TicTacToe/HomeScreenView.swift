//
//  HomeScreenView.swift
//  TicTacToe
//
//  Created by Robert Petro on 11/4/21.
//

import SwiftUI

struct HomeScreenView: View {
    
    @Binding var showingSettings: Bool
    @Binding var showingGame: Bool
    @Binding var diff: Int
    @State private var showDetail = false;
    
    var body: some View {
        ZStack {
            LinearGradient(gradient: Gradient(colors: [.black, .blue]),
                           startPoint: .top,
                           endPoint: .bottom)
                .ignoresSafeArea(.all)
            VStack {
                Text("tic-tac-toe")
                    .foregroundColor(.white)
                    .font(.system(size: 50, weight: .bold))
                    .padding()
                Spacer()
                Button {
                    diff = 2
                    showDetail.toggle()
                    showingGame.toggle()
                } label: {
                    DifficultyButton(title: "easy")
                        .animation(.spring(), value: showDetail)
                }
                Button {
                    diff = 3
                    showingGame.toggle()
                } label: {
                    DifficultyButton(title: "medium")
                }
                Button {
                    diff = 4
                    showingGame.toggle()
                } label: {
                    DifficultyButton(title: "hard")
                }
                Spacer()
                Button {
                    showingSettings.toggle()
                } label: {
                    Text("settings")
                        .frame(width:280, height: 50)
                        .background(Color.black)
                        .foregroundColor(.white)
                        .font(.system(size: 30, weight: .medium))
                        .cornerRadius(20)
                        .padding()
                }
            }
        }
    }
}

struct HomeScreenView_Previews: PreviewProvider {
    @State static var isShowingSettings = false
    @State static var isShowingGame = true
    @State static var diff = 4
    static var previews: some View {
        HomeScreenView(showingSettings: $isShowingSettings, showingGame: $isShowingGame, diff: $diff)
    }
}
