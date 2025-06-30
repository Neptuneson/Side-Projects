//
//  ContentView.swift
//  TicTacToe
//
//  Created by Robert Petro on 8/2/21.
//

import SwiftUI

struct MainGameView: View {
    
    let columns: [GridItem] = [GridItem(.flexible()),
                              GridItem(.flexible()),
                              GridItem(.flexible()),]
    
    @State private var moves: [Move?] = Array(repeating: nil, count: 9)
    @State private var isGameboardDisabled = false
    @State private var alertItem: AlertItem?
    @State private var wins = 0
    @State private var losses = 0
    @State private var draws = 0
    @Binding var showingGame: Bool
    @Binding var diff: Int
    
    var body: some View {
        ZStack {
            Background(topColor: .black, bottomColor: .blue)
            GeometryReader { geometry in
                VStack {
                    HStack {
                        ScoreDisplay(title: "wins", score: wins, geometry: geometry)
                        ScoreDisplay(title: "losses", score: losses, geometry: geometry)
                        ScoreDisplay(title: "draws", score: draws, geometry: geometry)
                    }
                    Spacer()
                    LazyVGrid(columns: columns, spacing: 5) {
                        ForEach(0..<9) { i in
                            ZStack {
                                Circle()
                                    .foregroundColor(.blue).opacity(0.5)
                                    .frame(width: geometry.size.width/3 - 15,
                                           height: geometry.size.width/3 - 15)
                                
                                Image(systemName: moves[i]?.indicator ?? "")
                                    .resizable()
                                    .frame(width: 40, height: 40)
                                    .foregroundColor(.white)
                            }
                            .onTapGesture {
                                if isSquareOccupied(in: moves, forIndex: i) {return}
                                moves[i] = Move(player: .human, boardIndex: i)
                                
                                if checkWinCondition(for: .human, in: moves) {
                                    alertItem = AlertContext.humanWin
                                    wins += 1
                                    return
                                }
                                
                                if checkForDraw(in: moves) {
                                    alertItem = AlertContext.draw
                                    draws += 1
                                    return
                                }
                                isGameboardDisabled = true
                                
                                DispatchQueue.main.asyncAfter(deadline: .now() + 0.7) {
                                    let computerPosition = detremineComputerMovePosition(in: moves, difficulty: diff)
                                    moves[computerPosition] = Move(player: .computer, boardIndex: computerPosition)
                                    isGameboardDisabled = false
                                    
                                    if checkWinCondition(for: .computer, in: moves) {
                                        alertItem = AlertContext.computerWin
                                        losses += 1
                                        return
                                    }
                                    if checkForDraw(in: moves) {
                                        alertItem = AlertContext.draw
                                        draws += 1
                                        return
                                    }
                                }
                            }
                        }
                    }
                    Spacer()
                    Button {
                        showingGame.toggle()
                    } label: {
                        Label("back", systemImage: "chevron.backward.circle")
                            .font(.system(size: 32, weight: .medium))
                            .foregroundColor(Color("drawsColor"))
                    }
                }
                .disabled(isGameboardDisabled)
                .padding()
                .alert(item: $alertItem, content: { alertItem in
                        Alert(title: alertItem.title,
                              message: alertItem.message,
                              dismissButton: .default(alertItem.buttonTitle, action: { resetGame()}))
                    
                })
            }
        }
    }
    func isSquareOccupied(in moves: [Move?], forIndex index: Int) -> Bool {
        return moves.contains(where: {$0?.boardIndex == index})
    }
    
    func detremineComputerMovePosition(in moves: [Move?], difficulty: Int) -> Int {
        // If AI can win, then win
        let winPatterns: Set<Set<Int>> = [[0,1,2], [3,4,5], [6,7,8], [0,3,6], [1,4,7], [2,5,8], [0,4,8], [2,4,6]]
            
        let computerMoves = moves.compactMap { $0 }.filter { $0.player == .computer }
        let computerPositions = Set(computerMoves.map { $0.boardIndex })
        
        if difficulty >= 4 {
            for pattern in winPatterns {
                let winPositions = pattern.subtracting(computerPositions)
                    
                if winPositions.count == 1 {
                    let isAvaiable = !isSquareOccupied(in: moves, forIndex: winPositions.first!)
                    if isAvaiable {return winPositions.first!}
                }
            }
        }
        
        //If AI can't win, then block
        let humanMoves = moves.compactMap { $0 }.filter { $0.player == .human }
        let humanPositions = Set(humanMoves.map { $0.boardIndex })
        
        if difficulty >= 3 {
            for pattern in winPatterns {
                let winPositions = pattern.subtracting(humanPositions)
                
                if winPositions.count == 1 {
                    let isAvaiable = !isSquareOccupied(in: moves, forIndex: winPositions.first!)
                    if isAvaiable {return winPositions.first!}
                }
            }
        }
        
        //If AI cant't block, then take middle square
        if difficulty >= 2 {
            let centerSquare = 4
            if !isSquareOccupied(in: moves, forIndex: centerSquare) {
                return 4
            }
        }
        
        //If AI can't take middle square, take random available square
        var movePosition = Int.random(in:0..<9)
            
        while isSquareOccupied(in: moves, forIndex: movePosition) {
            movePosition = Int.random(in: 0..<9)
        }
            
        return movePosition
    }
    
    func checkWinCondition(for player: Player, in moves: [Move?]) -> Bool {
        let winPatterns: Set<Set<Int>> = [[0,1,2], [3,4,5], [6,7,8], [0,3,6], [1,4,7], [2,5,8], [0,4,8], [2,4,6]]
        
        let playerMoves = moves.compactMap { $0 }.filter { $0.player == player }
        let playerPositions = Set(playerMoves.map{ $0.boardIndex })
        
        for pattern in winPatterns where pattern.isSubset(of: playerPositions) {return true}
        
        return false
    }
    
    func checkForDraw(in moves: [Move?]) -> Bool {
        return moves.compactMap{ $0 }.count == 9
    }
    
    func resetGame() {
        moves = Array(repeating: nil, count: 9)
    }
}

enum Player{
    case human, computer
}

struct Move {
    let player: Player
    let boardIndex: Int
    var indicator: String {
        return player == .human ? "xmark" : "circle"
    }
}

struct MainGameView_Previews: PreviewProvider {
    @State static var isShowingGame = true
    @State static var diff = 4
    static var previews: some View {
        MainGameView(showingGame: $isShowingGame, diff: $diff)
    }
}
