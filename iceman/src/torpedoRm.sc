;;; Sierra Script 1.0 - (do not remove this comment)
(script# 33)
(include sci.sh)
(use Main)
(use Intrface)
(use subMarine)
(use Submarine_806)
(use InitAllFeatures)
(use SolvePuzzle)
(use Approach)
(use GoToSaid)
(use Track)
(use LoadMany)
(use Grooper)
(use RFeature)
(use Sight)
(use Avoider)
(use Sound)
(use Motion)
(use Game)
(use User)
(use Actor)
(use System)

(public
	torpedoRm 0
)
(synonyms
	(floor down)
	(torpedo gun)
)

(local
	[local0 12] = [198 196 193 192 189 189 190 189 190 188 187 187]
	[local12 12] = [16 17 17 18 19 19 21 22 24 26 28 28]
	local24
)
(procedure (localproc_2222)
	(explosivesDoor stopUpd:)
	(cond 
		((and (not (ego has: 3)) (== numFlares 0)) (Print 33 68))
		((and (not (ego has: 3)) (== numFlares 4)) (Print 33 69))
		((not (ego has: 3)) (Print 33 70))
		((== numFlares 0) (Print 33 71))
		((== numFlares 4) (Print 33 72))
		(else (Print 33 73))
	)
	(HandsOn)
)

(instance torpedoRm of Rm
	(properties
		picture 33
		west 42
	)
	
	(method (init)
		(super init:)
		(User x: 13 y: 140)
		(LoadMany
			132
			13
			213
			19
			219
			33
			233
			87
			287
			86
			286
			48
			248
			85
			285
			88
			288
			24
			224
		)
		(entryDoor init:)
		(missileTube init:)
		(firstMissileHolder init:)
		(explosivesDoor init:)
		(missileOne init:)
		(missileGrabber init:)
		(conveyorBelt init:)
		(conveyorStand init:)
		(if (not (& (subMarine roomFlags?) $0400))
			(subMarine roomFlags: (| (subMarine roomFlags?) $0400))
			(torpedoMan
				init:
				setLoop: -1
				loop: 3
				heading: 0
				setCycle: Walk
				setLoop: Grooper
				setScript: torpedoManScript
			)
		)
		(if (>= (Submarine torpedosLeft?) 2)
			(addToPics add: missileTwo)
			(if (>= (Submarine torpedosLeft?) 3)
				(addToPics add: missileThree)
				(if (>= (Submarine torpedosLeft?) 4)
					(addToPics add: missileFour)
					(if (>= (Submarine torpedosLeft?) 5)
						(addToPics add: missileFive)
						(if (>= (Submarine torpedosLeft?) 6)
							(addToPics add: missileSix)
							(if (>= (Submarine torpedosLeft?) 7)
								(addToPics add: missileSeven)
								(if (>= (Submarine torpedosLeft?) 8)
									(addToPics add: missileEight)
								)
							)
						)
					)
				)
			)
		)
		(addToPics
			add: secondMissileHolder thirdMissileHolder panel
			eachElementDo: #init
			doit:
		)
		(self setRegions: 314)
		(InitAllFeatures)
		(ego
			init:
			illegalBits: 0
			ignoreActors: 0
			view: 232
			posn: 9 52
		)
		(doorScript register: 1)
		(if (& (subMarine roomFlags?) $0800)
			(conveyorBelt posn: 91 93 setPri: 8 setScript: 0 stopUpd:)
			(conveyorStand posn: 97 117 setPri: 7 stopUpd:)
			(missileGrabber setCel: 11 setPri: 4)
			(if (not (& (subMarine roomFlags?) $2000))
				(missileOne posn: 130 71 setPri: 9 setScript: 0 stopUpd:)
			)
			(missileTube posn: 220 134 stopUpd:)
		)
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said 'look[<at,around][/room,scene]') (Print 33 0) (Print 33 1) (Print 33 2) (Print 33 3))
			((Said 'look/floor') (Print 33 4))
		)
	)
	
	(method (newRoom newRoomNumber)
		(User x: -1 y: -1)
		(super newRoom: newRoomNumber)
	)
)

(instance torpedoMan of Act
	(properties
		y 55
		x 116
		view 132
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/man<old]>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 5))
					((GoToIfSaid self event self 30 0 33 6))
					((Said 'ask,get/name[<man]') (Print 33 7))
					((Said 'address')
						(if local24
							(Print 33 8)
						else
							(Print 33 9)
							(= local24 1)
						)
					)
				)
			)
		)
	)
	
	(method (cue)
		(super cue:)
		(self dispose:)
	)
)

(instance torpedoManScript of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0 (= seconds 3))
			(1
				(torpedoMan heading: 180)
				((torpedoMan looper?)
					doit: torpedoMan (torpedoMan heading?)
				)
				(= cycles 5)
			)
			(2
				(torpedoMan cel: 5)
				(Print 33 10)
				(= seconds 30)
			)
			(3
				(torpedoMan setScript: saltOutScript)
			)
		)
	)
)

(instance saltOutScript of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(User canControl: 0 canInput: 0)
				(Print 33 11 #time 5 #dispose)
				(torpedoMan
					illegalBits: 0
					ignoreActors:
					setMotion: MoveTo 10 60 self
				)
			)
			(1
				(cls)
				(entryDoor setMotion: MoveTo 0 7 self)
			)
			(2
				(entryDoor stopUpd:)
				(torpedoMan setMotion: MoveTo 10 52 self)
			)
			(3
				(entryDoor setMotion: MoveTo 0 58 self)
			)
			(4
				(if (not (conveyorBelt script?)) (HandsOn))
				(entryDoor stopUpd:)
				(torpedoMan cue:)
				(self dispose:)
			)
		)
	)
)

(instance panel of RPicView
	(properties
		y 67
		x 280
		view 233
		loop 6
		cel 2
		priority 3
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/control,panel]>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 12))
				)
			)
			((Said '/button>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 13))
					((GoToIfSaid self event 261 84 'push,press' 33 6))
					((Said 'push,press,(turn<on)') (conveyorBelt setScript: cycleEquipment))
				)
			)
		)
	)
)

(instance conveyorBelt of Act
	(properties
		y 93
		x 91
		view 233
		loop 5
		priority 8
		signal $4811
		illegalBits $0000
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			(
				(Said
					'[/conveyor,belt,(belt<conveyor),equipment,machine,hole]>'
				)
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 14))
					(
						(GoToIfSaid
							self
							event
							261
							84
							'cycle,check,begin,(turn<on)'
							33
							6
						)
					)
					((Said 'cycle,check,begin,(turn<on)') (conveyorBelt setScript: cycleEquipment))
					((GoToIfSaid self event 105 95 0 33 6))
					((Said 'measure')
						(HandsOff)
						(ego heading: 180)
						((ego looper?) doit: ego (ego heading?) self)
						(cond 
							((not (& (subMarine roomFlags?) $0800)) (Print 33 15))
							((not (ego has: 13)) (Print 33 16))
							((not (& (subMarine roomFlags?) $4000)) (Print 33 17))
							((not (& (subMarine roomFlags?) $1000)) (Print 33 18))
							(else (Print 33 19))
						)
					)
					((Said 'check,examine,examine')
						(cond 
							(
								(and
									(& (subMarine roomFlags?) $0800)
									(not (& (subMarine roomFlags?) $1000))
									(not (& (subMarine roomFlags?) $4000))
								)
								(Print 33 20)
								(ego get: 12)
								(subMarine invStatus1: 4)
								(subMarine roomFlags: (| (subMarine roomFlags?) $4000))
							)
							(
								(and
									(not (& (subMarine roomFlags?) $1000))
									(& (subMarine roomFlags?) $4000)
								)
								(Print 33 21)
							)
							((& (subMarine roomFlags?) $1000) (Print 33 22))
							(else (Print 33 23))
						)
					)
					((Said 'cease') (Print 33 24))
					((Said 'fix,repair') (ego setScript: fixEquipment))
				)
			)
			((Said 'use/pin') (if (ego has: 9) (Print 33 25) else (DontHave)))
			(
				(GoToIfSaid
					self
					event
					105
					95
					'insert,install,(adjust[<in,on])/pin[/conveyor,equipment]'
					33
					6
				)
			)
			(
				(Said
					'insert,install,(adjust[<in,on])/pin[/conveyor,equipment]'
				)
				(ego setScript: installGoodCotterPin)
			)
			((Said '/cylinder>')
				(cond 
					((Said 'measure')
						(cond 
							((not (ego has: 12)) (Print 33 26))
							((not (ego has: 13)) (Print 33 27))
							(
							(and (ego has: 12) (!= (subMarine invStatus1?) 4))
								(Printf
									33
									28
									(switch (subMarine invStatus1?)
										(1 {3})
										(2 {4})
										(3 {6})
									)
								)
								(if (subMarine cylDiam?)
									(Printf
										33
										29
										(switch (subMarine cylDiam?)
											(1 {1"})
											(2 {1.5"})
											(3 {2"})
										)
									)
								)
								(if (subMarine holeSize?)
									(Printf
										33
										30
										(switch (subMarine holeSize?)
											(1 {1/32"})
											(2 {1/16"})
											(3 {1/8"})
											(4 {1/4"})
											(5 {1/2"})
											(6 {3/4"})
											(7 {1"})
										)
									)
								)
								(if (& (subMarine roomFlags?) $0004)
									(Print 33 31)
								else
									(Print 33 32)
								)
							)
							(else (Print 33 33) (SolvePuzzle subMarine 406 32 5))
						)
					)
					(
					(TurnIfSaid self event 'examine,examine,look[<at]/*'))
					((Said 'examine,examine,look[<at]')
						(cond 
							((not (& (subMarine roomFlags?) $4000)) (Print 33 34))
							((& (Submarine flags?) $0100) (Print 33 35))
							((& (subMarine roomFlags?) $1000) (Print 33 36))
							(else ((inventory at: 12) showSelf:))
						)
					)
					((GoToIfSaid self event 105 95 0 33 6))
					((Said 'get,detach/*')
						(HandsOff)
						(ego heading: 180)
						((ego looper?) doit: ego (ego heading?) self)
						(cond 
							((not (& (subMarine roomFlags?) $0800)) (Print 33 15))
							((& (Submarine flags?) $0100) (Print 33 37))
							((& (subMarine roomFlags?) $1000)
								(Print 33 38)
								(ego get: 12)
								(subMarine roomFlags: (& (subMarine roomFlags?) $efff))
							)
							(
							(and (ego has: 12) (== (subMarine invStatus1?) 4)) (Print 33 39))
							(else
								(Print 33 40)
								(ego get: 12)
								(subMarine invStatus1: 4)
								(subMarine roomFlags: (| (subMarine roomFlags?) $4000))
							)
						)
					)
					(
					(Said '(adjust<in,on),repair,fix,install,insert,replace') (ego setScript: fixEquipment))
				)
			)
		)
	)
	
	(method (cue)
		(super cue:)
		(HandsOn)
	)
)

(instance fixEquipment of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(cond 
					((not (& (subMarine roomFlags?) $0800)) (Print 33 15))
					((& (Submarine flags?) $0100) (Print 33 41))
					(
						(and
							(& (subMarine roomFlags?) $1000)
							(not (& (Submarine flags?) $0100))
						)
						(Print 33 42)
					)
					(
						(or
							(not (ego has: 12))
							(and (ego has: 12) (== (subMarine invStatus1?) 4))
						)
						(Print 33 43)
					)
					((!= (subMarine invStatus1?) 3) (Print 33 44))
					((!= (subMarine cylDiam?) 1) (Print 33 45))
					((not (& (subMarine roomFlags?) $0004)) (Print 33 46))
					(else
						(Print 33 47)
						(Print 33 48)
						(ego put: 12)
						(subMarine roomFlags: (| (subMarine roomFlags?) $1000))
						(SolvePuzzle subMarine 407 1024 1)
					)
				)
				(self dispose:)
			)
		)
	)
)

(instance installGoodCotterPin of Script
	(properties)
	
	(method (changeState newState &tmp temp0)
		(switch (= state newState)
			(0
				(= temp0 1)
				(HandsOff)
				(ego heading: 180)
				((ego looper?) doit: ego (ego heading?))
				(cond 
					((not (ego has: 9)) (Print 33 49) (= temp0 0))
					((& (Submarine flags?) $0100) (Print 33 50) (= temp0 0))
					((not (& (subMarine roomFlags?) $1000)) (Print 33 51) (= temp0 0))
					((not (ego has: 15)) (Print 33 52) (= temp0 0))
					((not (subMarine holeSize?)) (Print 33 53) (= temp0 0))
					((< (subMarine holeSize?) 4) (Print 33 54) (= temp0 0))
					((> (subMarine holeSize?) 4) (Print 33 55) (= temp0 0))
				)
				(if temp0 (= cycles 1) else (self dispose:) (HandsOn))
			)
			(1
				(ego
					setAvoider: Avoid
					setMotion: Approach conveyorBelt 60 self
				)
			)
			(2
				(theSound number: (SoundFX 33) loop: 4 play:)
				(Print 33 56)
				(ego setAvoider: 0 put: 9)
				(theGame changeScore: 1)
				(subMarine cylDiam: 0)
				(subMarine holeSize: 0)
				(subMarine roomFlags: (& (subMarine roomFlags?) $fffb))
				(Submarine flags: (| (Submarine flags?) $0100))
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance cycleEquipment of Script
	(properties)
	
	(method (doit)
		(super doit:)
		(if
			(and
				(< 2 state)
				(< state 9)
				(not (& (Submarine flags?) $0100))
				(not script)
			)
			(missileOne
				posn: [local0 (missileGrabber cel?)] [local12 (missileGrabber cel?)]
			)
		)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(cond 
					(
						(and
							(& (subMarine roomFlags?) $0800)
							(not (& (subMarine roomFlags?) $1000))
						)
						(Print 33 57)
						(self dispose:)
					)
					(
						(and
							(& (subMarine roomFlags?) $0800)
							(& (subMarine roomFlags?) $1000)
							(not (& (Submarine flags?) $0100))
						)
						(Print 33 58)
						(self dispose:)
					)
					((& (subMarine roomFlags?) $2000) (Print 33 59) (self dispose:))
					(else
						(HandsOff)
						(ego heading: 225)
						((ego looper?) doit: ego (ego heading?))
						(= cycles 5)
					)
				)
			)
			(1
				(if (& (Submarine flags?) $0100)
					(self setScript: cycleEquipmentPartTwoScript)
				else
					(= cycles 1)
				)
			)
			(2
				(if
					(and
						(cast contains: torpedoMan)
						(!= (torpedoMan script?) saltOutScript)
					)
					(torpedoMan setScript: saltOutScript self)
				else
					(= cycles 1)
				)
			)
			(3
				(ego setPri: 4 stopUpd:)
				(missileOne setPri: 6)
				(conveyorStand setPri: 4)
				(conveyorBelt setPri: 5)
				(missileGrabber setPri: 7)
				(theSound number: (SoundFX 87) loop: -1 play:)
				(conveyorBelt setMotion: MoveTo 154 61 self)
				(conveyorStand setMotion: Track conveyorBelt 6 24)
			)
			(4 (= seconds 2))
			(5
				(conveyorStand setMotion: 0)
				(theSound number: (SoundFX 85) loop: 1 play:)
				(conveyorBelt setMotion: MoveTo 154 49 self)
			)
			(6
				(theSound number: (SoundFX 86) loop: 1 play:)
				(= cycles 6)
			)
			(7
				(theSound number: (SoundFX 48) loop: 1 play:)
				(missileGrabber setCycle: End self)
			)
			(8
				(theSound number: (SoundFX 86) loop: 1 play: self)
			)
			(9
				(if (not (& (Submarine flags?) $0100))
					(Print 33 60 #at 190 120)
				)
				(= cycles 1)
			)
			(10
				(missileOne setMotion: MoveTo 190 29 self)
			)
			(11
				(missileGrabber stopUpd:)
				(theSound number: (SoundFX 85) loop: 1 play:)
				(conveyorBelt setMotion: MoveTo 154 61 self)
				(missileOne setMotion: Track conveyorBelt 36 -20)
			)
			(12
				(theSound number: (SoundFX 86) loop: 1 play: self)
			)
			(13
				(theSound number: (SoundFX 87) loop: -1 play:)
				(conveyorBelt setMotion: MoveTo 91 93 self)
				(conveyorStand setMotion: Track conveyorBelt 6 24)
				(missileOne setMotion: Track conveyorBelt 36 -20)
			)
			(14
				(theSound number: (SoundFX 86) loop: 1 play:)
				(conveyorStand stopUpd:)
				(missileOne setMotion: 0 posn: 130 71)
				(if (not (& (Submarine flags?) $0100))
					(Print 33 61 #at 5 6)
				)
				(= cycles 1)
			)
			(15
				(theSound number: (SoundFX 88) loop: 1 play:)
				(missileTube setMotion: MoveTo 220 134 self)
			)
			(16
				(self setScript: cycleEquipmentPartTwoScript)
			)
		)
	)
)

(instance cycleEquipmentPartTwoScript of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(theSound stop:)
				(if (& (Submarine flags?) $0100)
					(Print 33 62 #at 0 113)
				else
					(theSound number: (SoundFX 24) loop: -1 play:)
				)
				(= cycles 1)
			)
			(1
				(conveyorBelt setPri: 8)
				(conveyorStand setPri: (- (conveyorBelt priority?) 1))
				(missileGrabber
					setPri: (+ (firstMissileHolder priority?) 1)
				)
				(missileOne setPri: (+ (conveyorBelt priority?) 1))
				(missileTube stopUpd:)
				(ego setPri: -1 ignoreActors: 0 stopUpd:)
				(HandsOn)
				(subMarine roomFlags: (| (subMarine roomFlags?) $0800))
				(if (& (Submarine flags?) $0100)
					(missileOne setMotion: 0)
					(self setScript: goodConveyorScript)
				else
					(missileOne setMotion: 0)
					(self setScript: messedUpConveyorScript self)
				)
			)
			(2
				(missileOne setScript: 0 stopUpd:)
				(conveyorBelt setScript: 0 stopUpd:)
				(theSound stop:)
				(HandsOn)
				(self dispose:)
			)
		)
	)
)

(instance goodConveyorScript of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(conveyorBelt setCycle: Fwd)
				(missileOne setPri: 11 setMotion: MoveTo 241 107 self)
			)
			(1
				(conveyorBelt setCycle: 0 stopUpd:)
				(missileOne setMotion: MoveTo 293 124 self)
			)
			(2
				(HandsOn)
				(subMarine roomFlags: (| (subMarine roomFlags?) $2000))
				(missileOne stopUpd:)
				(self dispose:)
			)
		)
	)
)

(instance messedUpConveyorScript of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(missileOne
					posn: (+ (missileOne x?) 2) (+ (missileOne y?) 2)
				)
				(conveyorBelt
					posn: (- (conveyorBelt x?) 1) (- (conveyorBelt y?) 1)
				)
				(conveyorBelt setCycle: CT 3 1 self)
			)
			(1
				(missileOne
					posn: (- (missileOne x?) 2) (- (missileOne y?) 2)
				)
				(conveyorBelt
					posn: (+ (conveyorBelt x?) 1) (+ (conveyorBelt y?) 1)
				)
				(conveyorBelt setCycle: CT 0 -1 self)
			)
			(2
				(if (> (++ register) 3)
					(= cycles 1)
				else
					(self init:)
				)
			)
			(3
				(Print 33 63 #at 0 113)
				(self dispose:)
			)
		)
	)
)

(instance theSound of Sound
	(properties
		priority 2
	)
)

(instance missileTube of Act
	(properties
		y 147
		x 274
		view 233
		loop 7
		priority 10
		signal $4811
		illegalBits $0000
	)
)

(instance firstMissileHolder of Prop
	(properties
		y 68
		x 240
		view 233
		signal $0011
		nsTop 19
		nsLeft 183
		nsBottom 60
		nsRight 244
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/arm<hydraulic]>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 64))
				)
			)
		)
	)
)

(instance secondMissileHolder of RPicView
	(properties
		y 43
		x 141
		view 233
		priority 0
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/arm<hydraulic]>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 64))
				)
			)
		)
	)
)

(instance thirdMissileHolder of RPicView
	(properties
		y 148
		x 75
		view 233
		cel 2
		priority 11
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/arm<hydraulic]>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 64))
				)
			)
		)
	)
)

(instance missileGrabber of Act
	(properties
		y 16
		x 197
		view 233
		loop 1
		priority 1
		signal $4811
		illegalBits $0000
	)
)

(instance missileOne of Act
	(properties
		y 16
		x 198
		yStep 1
		view 233
		loop 4
		cel 3
		priority 1
		signal $4811
		illegalBits $0000
		xStep 1
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/missile]>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 65))
				)
			)
		)
	)
)

(instance missileTwo of RPicView
	(properties
		y 37
		x 192
		view 233
		loop 4
		cel 1
		priority 1
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/torpedo]>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 66))
				)
			)
		)
	)
)

(instance missileThree of RPicView
	(properties
		y -10
		x 103
		view 233
		loop 4
		priority 1
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/missile]>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 65))
				)
			)
		)
	)
)

(instance missileFour of RPicView
	(properties
		y 35
		x 118
		view 233
		loop 4
		cel 2
		priority 1
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/decoy]>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 67))
				)
			)
		)
	)
)

(instance missileFive of RPicView
	(properties
		y 109
		x 96
		view 233
		loop 4
		cel 1
		priority 11
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/torpedo]>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 66))
				)
			)
		)
	)
)

(instance missileSix of RPicView
	(properties
		y 129
		x 106
		view 233
		loop 4
		priority 9
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/missile]>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 65))
				)
			)
		)
	)
)

(instance missileSeven of RPicView
	(properties
		y 123
		x 6
		view 233
		loop 4
		cel 2
		priority 11
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/decoy]>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 67))
				)
			)
		)
	)
)

(instance missileEight of RPicView
	(properties
		y 104
		x 13
		view 233
		loop 4
		cel 2
		priority 11
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/decoy]>')
				(cond 
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (Print 33 67))
				)
			)
		)
	)
)

(instance conveyorStand of Act
	(properties
		y 117
		x 97
		view 233
		cel 3
		priority 7
		signal $4811
		illegalBits $0000
	)
)

(instance entryDoor of Act
	(properties
		y 3
		view 233
		cel 4
		priority 2
		signal $6811
		illegalBits $0000
	)
	
	(method (doit)
		(super doit:)
		(if
			(and
				(User controls?)
				(== (ego onControl: 1) 4)
				(not (self script?))
			)
			(HandsOff)
			(entryDoor setScript: doorScript)
		)
	)
	
	(method (stopUpd)
		(super stopUpd: &rest)
		(theSound number: (SoundFX 19) loop: 1 play:)
	)
	
	(method (cue)
		(super cue: &rest)
		(self stopUpd:)
	)
	
	(method (setMotion)
		(super setMotion: &rest)
		(theSound number: (SoundFX 13) loop: 1 play:)
	)
)

(instance doorScript of Script
	(properties)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(ego illegalBits: 0 ignoreActors:)
				(entryDoor setMotion: MoveTo 0 7 self)
			)
			(1
				(if register
					(ego setMotion: MoveTo 27 73 self)
				else
					(ego setMotion: MoveTo 10 52 self)
				)
			)
			(2
				(entryDoor setMotion: MoveTo 0 58 entryDoor)
				(ego illegalBits: -32768 ignoreActors: 0 setPri: -1)
				(if (not register) (curRoom newRoom: 42))
				(= register 0)
				(HandsOn)
				(client setScript: 0)
			)
		)
	)
)

(instance explosivesDoor of Prop
	(properties
		y 23
		x 37
		view 233
		loop 8
		priority 4
		signal $0001
	)
	
	(method (doit)
		(super doit:)
		(if
			(and
				(== (ego onControl: 1) 2048)
				(not (CantBeSeen self ego))
				(not (self script?))
			)
			(explosivesDoor setScript: openExplosivesDoorScript self)
		)
	)
	
	(method (handleEvent event)
		(cond 
			((super handleEvent: event))
			((Said '[/door,compartment,closet,cabinet]>')
				(cond 
					((Said 'look<in')
						(if (== (ego onControl: 1) 2048)
							(localproc_2222)
						else
							(Print 33 74)
						)
					)
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'close') (DontNeedTo))
					((Said 'look[<at]') (Print 33 75))
					((GoToIfSaid self event self 10 'open' 33 6))
					((Said 'open') (ego setMotion: MoveTo 39 31))
				)
			)
			((Said '/flare>')
				(cond 
					((not cel) (event claimed: 1) (CantSee))
					((TurnIfSaid self event 'look[<at]'))
					((Said 'look[<at]')
						(if (>= numFlares 8)
							(event claimed: 0)
						else
							(Print 33 76)
						)
					)
					((Said 'get')
						(cond 
							((not numFlares)
								(= numFlares (+ numFlares 4))
								(ego get: 7)
								(Print 33 77)
							)
							((== numFlares 4)
								(= numFlares (+ numFlares 4))
								(theGame changeScore: 1)
								(Print 33 78)
							)
							(else (CantSee))
						)
					)
					((Said 'light') (CantDo))
				)
			)
			((Said '/bomb>')
				(cond 
					((not cel) (event claimed: 1) (CantSee))
					((TurnIfSaid self event 'look[<at]/*'))
					((Said 'look[<at]') (if (ego has: 3) (event claimed: 0) else (Print 33 79)))
					((Said 'get')
						(if (ego has: 3)
							(AlreadyTook)
						else
							(Print 33 80)
							(ego get: 3)
						)
					)
				)
			)
		)
	)
	
	(method (stopUpd)
		(super stopUpd: &rest)
		(theSound number: (SoundFX 19) loop: 1 play:)
	)
	
	(method (cue)
		(super cue: &rest)
		(self setScript: 0 stopUpd:)
	)
)

(instance openExplosivesDoorScript of Script
	(properties)
	
	(method (doit)
		(if
			(and
				(!= (ego onControl: 1) 2048)
				(client cel?)
				(not (client cycler?))
				(CantBeSeen client ego 180 20)
			)
			(client setCycle: Beg client)
		)
		(super doit:)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(HandsOff)
				(explosivesDoor setCycle: End self)
			)
			(1 (localproc_2222))
		)
	)
)
