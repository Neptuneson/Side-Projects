//
//  DifficultyButton.swift
//  TicTacToe
//
//  Created by Robert Petro on 11/4/21.
//

import SwiftUI

struct DifficultyButton: View {
    var title: String
    var body: some View {
        Text(title)
            .frame(width:280, height: 50)
            .background(Color.blue)
            .foregroundColor(.white)
            .font(.system(size: 30, weight: .medium))
            .cornerRadius(20)
            .padding()
    }
}
