//
//  ScoreDisplay.swift
//  TicTacToe
//
//  Created by Robert Petro on 11/4/21.
//

import SwiftUI

struct ScoreDisplay: View {
    var title: String
    var score: Int
    var geometry: GeometryProxy
    var body: some View {
        Text("\(title): \(score)")
            .font(.system(size: geometry.size.width/23, weight: .bold))
            .foregroundColor(Color("winsColor"))
            .padding()
    }
}
