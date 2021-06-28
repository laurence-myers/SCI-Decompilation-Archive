;;; Sierra Script 1.0 - (do not remove this comment)
(script# 770)
(include game.sh) (include "770.shm")
(use Main)
(use LBRoom)
(use Talker)
(use RandCyc)
(use LoadMany)
(use Motion)
(use Actor)
(use System)

(public
	rm770 0
	O_Riley 19
)

(local
	theSeq
	murdererCued
	badguy1Cued
	badguy2Cued
)
(instance rm770 of LBRoom
	(properties
		picture 770
		style FADEOUT
	)
	
	(method (init)
		(LoadMany RES_VIEW 770 771)
		(LoadMany RES_SOUND 770)
		(super init:)
		(theMusic number: 771 loop: -1 flags: 1 play:)
		(theIconBar disable:)
		(theGame setCursor: INVIS_CURSOR)
		(bird init: setScript: sFly)
		(bird2 init: setScript: sLand 0 4)
		(murderer init:)
		(cop2 init: setScript: (sRandomScr new:))
		(cop3 init:)
		(cop5 init:)
		(cop6 init:)
		(badguy1 init: setScript: (sRandomScr new:))
		(badguy2 init: setScript: sRandomScr2)
		(badguy3 init:)
		(self setScript: sRunIt)
	)
	
	(method (dispose)
		(theMusic fade: 0 12 30 1)
		(super dispose:)
	)
)

(instance sFly of Script

	(method (changeState newState)
		(switch (= state newState)
			(0
				(client setLoop: (Random 0 3) setPri: 15 setCycle: Forward)
				(switch (client loop?)
					(0
						(client posn: 329 30 setMotion: MoveTo -10 17 self)
					)
					(1
						(client posn: -10 17 setMotion: MoveTo 329 30 self)
					)
					(2
						(client posn: -10 95 setMotion: MoveTo 174 -15 self)
					)
					(3
						(client posn: 329 136 setMotion: MoveTo 198 -17 self)
					)
				)
			)
			(1 (self changeState: 0))
		)
	)
)

(instance sLand of Script
	
	(method (changeState newState &tmp toX toY)
		(switch (= state newState)
			(0
				(= toX (if (== register 4) 199 else 171))
				(= toY (if (== register 4) 116 else 124))
				(client
					setLoop: register
					setCycle: Forward
					setMotion: MoveTo toX toY self
				)
			)
			(1
				(client setLoop: (+ register 2) setCycle: EndLoop self)
			)
			(2
				(client
					setLoop: (+ register 4)
					cycleSpeed: 10
					setCycle: RandCycle
				)
				(if (== client bird2)
					(bird3 init: setScript: (sLand new:) 0 5)
				)
			)
		)
	)
)

(instance sRunIt of Script
	
	(method (doit)
		(super doit:)
		(if
			(and
				(not murdererCued)
				(== (murderer cel?) 4)
				(== (murderer loop?) 0)
			)
			(theMusic2 number: 770 flags: 1 loop: 1 play: murderer)
			(= murdererCued TRUE)
		)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= theSeq 1)
				(= cycles 1)
			)
			(1
				(messager say: N_ROOM NULL NULL theSeq self)
			)
			(2
				(murderer setCycle: EndLoop self)
			)
			(3
				(if (< (++ theSeq) 4)
					(self changeState: 1)
				else
					(messager say: N_ROOM NULL NULL 4 self)
				)
			)
			(4
				(murderer setCycle: CycleTo 4 1 self)
			)
			(5
				(theMusic2 number: 770 flags: mNOPAUSE loop: 1 play:)
				(murderer loop: 1 cel: 0 setCycle: EndLoop self)
			)
			(6 (messager say: N_ROOM NULL NULL 5 self))
			(7 (messager say: N_ROOM NULL NULL 6 self))
			(8
				(theMusic number: 772 flags: mNOPAUSE loop: 1 play: self)
			)
			(9
				(curRoom newRoom: (if (== global126 1) 775 else 785))
			)
		)
	)
)

(instance sRandomScr of Script
	
	(method (doit)
		(super doit:)
		(if
			(and
				(not badguy1Cued)
				(!= msgType CD_MSG)
				(== (client cel?) 4)
				(!= (client loop?) 5)
			)
			(theMusic2 number: 770 flags: mNOPAUSE loop: 1 play: badguy1)
			(= badguy1Cued TRUE)
		)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(0 (= ticks (Random 30 120)))
			(1 (client setCycle: EndLoop self))
			(2 (self changeState: 0))
		)
	)
)

(instance sRandomScr2 of Script
	
	(method (doit)
		(super doit:)
		(if (and (not badguy2Cued) (!= msgType CD_MSG) (== (client cel?) 4))
			(theMusic2 number: 770 flags: mNOPAUSE loop: 1 play: badguy2)
			(= badguy2Cued 1)
		)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(0 (= ticks (Random 30 120)))
			(1 (client setCycle: EndLoop self))
			(2 (self changeState: 0))
		)
	)
)

(instance bird of Actor
	(properties
		x -10
		y 17
		view 771
		loop 1
		signal (| ignrAct ignrHrz)
		cycleSpeed 0
		moveSpeed 0
	)
)

(instance bird2 of Actor
	(properties
		x 329
		y 8
		view 771
		loop 4
		signal (| ignrAct ignrHrz)
		cycleSpeed 0
		moveSpeed 0
	)
)

(instance bird3 of Actor
	(properties
		x 58
		y -18
		view 771
		loop 5
		signal (| ignrAct ignrHrz)
		cycleSpeed 0
		moveSpeed 0
	)
)

(instance murderer of Prop
	(properties
		x 162
		y 176
		view 770
		cel 9
		cycleSpeed 15
	)
	
	(method (cue)
		(= murdererCued FALSE)
		(super cue:)
	)
)

(instance cop2 of Prop
	(properties
		x 207
		y 45
		view 770
		loop 5
		cel 8
		cycleSpeed 15
	)
)

(instance cop3 of Prop
	(properties
		x 243
		y 49
		view 770
		loop 6
		cel 6
	)
)

(instance cop5 of Prop
	(properties
		x 275
		y 155
		view 770
		loop 6
		cel 5
	)
)

(instance cop6 of Prop
	(properties
		x 45
		y 151
		view 770
		loop 6
		cel 4
	)
)

(instance badguy1 of Prop
	(properties
		x 280
		y 131
		view 770
		loop 3
		cel 3
		cycleSpeed 15
	)
	
	(method (cue)
		(= badguy1Cued FALSE)
		(super cue:)
	)
)

(instance badguy2 of Prop
	(properties
		x 247
		y 154
		view 770
		loop 2
		cycleSpeed 15
	)
	
	(method (cue)
		(= badguy2Cued FALSE)
		(super cue:)
	)
)

(instance badguy3 of Prop
	(properties
		x 313
		y 136
		view 770
		loop 4
	)
)

(instance O_Riley of Narrator
	(properties
		x 10
		y 155
		modeless TRUE
		back 15
		name "O'Riley"
	)
	
	(method (init)
		(= font userFont)
		(super init: &rest)
	)
)
