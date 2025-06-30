//
//  Background.swift
//  TicTacToe
//
//  Created by Robert Petro on 11/8/21.
//

import SwiftUI

struct Background: View {
    var topColor: Color
    var bottomColor: Color
    var body: some View {
        LinearGradient(gradient: Gradient(colors: [topColor, bottomColor]), startPoint: .top, endPoint: .bottom)
            .ignoresSafeArea()
    }
}
