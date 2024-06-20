//
//  ColorPicker.swift
//  TicTacToe
//
//  Created by Robert Petro on 11/8/21.
//

import SwiftUI

struct ColorPicker: View {
    
    @State var topColor = Color.black
    @State var bottomColor = Color.blue
    
    var body: some View {
        ZStack {
            Background(topColor: topColor, bottomColor: bottomColor)
            VStack {
                Text("change apperence")
                    .foregroundColor(.white)
                    .font(.system(size: 30, weight: .medium))
                    .padding()
                HStack {
                    VStack {
                        Button {
                            topColor = .red
                            bottomColor = .red
                        } label: {
                            Text("Solid red")
                                .frame(width:140, height: 50)
                                .background(Color.blue)
                                .foregroundColor(.white)
                                .font(.system(size: 20, weight: .medium))
                                .cornerRadius(20)
                                .padding()
                        }
                        Button {
                            topColor = .green
                            bottomColor = .green
                        } label: {
                            Text("solid green")
                                .frame(width:140, height: 50)
                                .background(Color.blue)
                                .foregroundColor(.white)
                                .font(.system(size: 20, weight: .medium))
                                .cornerRadius(20)
                                .padding()
                        }
                        Button {
                            topColor = .blue
                            bottomColor = .blue
                        } label: {
                            Text("solid blue")
                                .frame(width:140, height: 50)
                                .background(Color.blue)
                                .foregroundColor(.white)
                                .font(.system(size: 20, weight: .medium))
                                .cornerRadius(20)
                                .padding()
                        }
                    }
                    VStack {
                        Button {
                            topColor = .black
                            bottomColor = .red
                        } label: {
                            Text("combo 1")
                                .frame(width:140, height: 50)
                                .background(Color.blue)
                                .foregroundColor(.white)
                                .font(.system(size: 20, weight: .medium))
                                .cornerRadius(20)
                                .padding()
                        }
                        Button {
                            topColor = .black
                            bottomColor = .green
                        } label: {
                            Text("combo 2")
                                .frame(width:140, height: 50)
                                .background(Color.blue)
                                .foregroundColor(.white)
                                .font(.system(size: 20, weight: .medium))
                                .cornerRadius(20)
                                .padding()
                        }
                        Button {
                            topColor = .black
                            bottomColor = .blue
                        } label: {
                            Text("combo 3")
                                .frame(width:140, height: 50)
                                .background(Color.blue)
                                .foregroundColor(.white)
                                .font(.system(size: 20, weight: .medium))
                                .cornerRadius(20)
                                .padding()
                        }
                    }
                }
            }
        }
    }
}

struct ColorPicker_Previews: PreviewProvider {
    static var previews: some View {
        ColorPicker()
    }
}
