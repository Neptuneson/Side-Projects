//
//  SettingsView.swift
//  TicTacToe
//
//  Created by Robert Petro on 11/4/21.
//

import SwiftUI

struct SettingsView: View {
    
    @Binding var showingSettings: Bool
    @State var showingCredits = false
    @State var isSoundOn = true
    @State var showingColors = false
    
    var body: some View {
        ZStack {
            LinearGradient(gradient: Gradient(colors: [.black, .blue]),
                           startPoint: .top,
                           endPoint: .bottom)
                .ignoresSafeArea(.all)
            VStack {
                Button {
                    showingSettings.toggle()
                } label: {
                    Label("back", systemImage: "chevron.backward.circle")
                        .font(.system(size: 32, weight: .medium))
                        .foregroundColor(Color("drawsColor"))
                }
                Spacer()
                Button {
                    showingColors.toggle()
                } label: {
                    Text("change apperence")
                        .frame(width:280, height: 50)
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .font(.system(size: 30, weight: .medium))
                        .cornerRadius(20)
                        .padding()
                }
                Button {
                    showingCredits.toggle()
                } label: {
                    Text("Credits")
                        .frame(width:280, height: 50)
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .font(.system(size: 30, weight: .medium))
                        .cornerRadius(20)
                        .padding()
                }
                Spacer()
            }
        }
    }
}

struct SettingsView_Previews: PreviewProvider {
    @State static var isShowingSettings = true
    static var previews: some View {
        SettingsView(showingSettings: $isShowingSettings)
    }
}
