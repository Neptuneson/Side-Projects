//
//  Alert.swift
//  TicTacToe
//
//  Created by Robert Petro on 8/2/21.
//

import SwiftUI

struct AlertItem: Identifiable {
    let id = UUID()
    var title: Text
    var message: Text
    var buttonTitle: Text
}

struct AlertContext {
    static let humanWin = AlertItem(title: Text("Win!"),
                             message: Text("You're Smart!"),
                             buttonTitle: Text("Restart"))
    
    static let computerWin = AlertItem(title: Text("Lose!"),
                             message: Text("Try Again"),
                             buttonTitle: Text("Restart"))
    
    static let draw = AlertItem(title: Text("Draw!"),
                             message: Text("Well Played"),
                             buttonTitle: Text("Restart"))
}
