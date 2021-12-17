;;; Sierra Script 1.0 - (do not remove this comment)
(script# 64917)
(include game.sh)
(use Main)
(use Styler)
(use DText)
(use Array)
(use User)
(use Actor)
(use System)


(class Plane of Object
	(properties
		scratch 0
		resX -1
		resY -1
		left 0
		top 0
		right 0
		bottom 0
		inLeft 0
		inTop 0
		inRight 0
		inBottom 0
		vanishingX 0
		vanishingY 0
		coordType 0
		picture -1
		style $0000
		priority -1
		back 0
		bitmap 0
		casts 0
		mirrored 0
		theFtrs 0
		extMH 0
	)
	
	(method (init param1 param2 param3 param4 param5 param6 param7 param8)
		(planes add: self)
		(if (== resX -1) (= resX screenWidth))
		(if (== resY -1) (= resY screenHeight))
		(if argc
			(self setRect: param1 param2 param3 param4)
			(if (> argc 4)
				(self setInsetRect: param5 param6 param7 param8)
			)
		)
		(AddPlane self)
		(= casts (Set new:))
		(= theFtrs (EventHandler new:))
	)
	
	(method (doit)
		(if casts (casts doit:))
	)
	
	(method (dispose)
		(casts delete: cast dispose:)
		(theFtrs eachElementDo: #dispose dispose:)
		(if extMH (extMH release: dispose:))
		(= casts (= bitmap (= theFtrs (= extMH 0))))
		(planes delete: self)
		(DeletePlane self)
		(super dispose:)
	)
	
	(method (addCast param1)
		(casts add: (param1 add: plane: self yourself:))
	)
	
	(method (deleteCast param1)
		(casts delete: (param1 plane: 0 yourself:))
	)
	
	(method (drawPic pic theStyle)
		(= picture pic)
		(if (> argc 1) (= style theStyle))
		(= mirrored (if (& style $0400) 1 else 0))
		(UpdatePlane self)
		(Styler doit: self style)
	)
	
	(method (findCast param1 &tmp castsNextNode temp1)
		(= castsNextNode (KList 3 (casts elements?)))
		(while castsNextNode
			(casts nextNode: (KList 6 castsNextNode))
			(= temp1 (KList 8 castsNextNode))
			(if (not (KString StrCmp (temp1 name?) param1))
				(return temp1)
			)
			(= castsNextNode (casts nextNode?))
		)
		(return 0)
	)
	
	(method (onMe theObjOrX theY &tmp theObjOrXX theObjOrXY)
		(if (== argc 1)
			(= theObjOrXX (theObjOrX x?))
			(= theObjOrXY (theObjOrX y?))
		else
			(= theObjOrXX theObjOrX)
			(= theObjOrXY theY)
		)
		(return
			(if
				(and
					(<= left theObjOrXX)
					(<= theObjOrXX right)
					(<= top theObjOrXY)
				)
				(<= theObjOrXY bottom)
			else
				0
			)
		)
	)
	
	(method (posn param1 param2 param3 &tmp temp0 temp1 curRoomPlaneLeft curRoomPlaneTop theLastScreenX theLastScreenY curRoomPlane temp7)
		(cond 
			((> argc 2)
				(= curRoomPlaneLeft (param3 left:))
				(= curRoomPlaneTop (param3 top?))
				(= theLastScreenX (param3 right:))
				(= theLastScreenY (param3 bottom?))
			)
			(
			(and curRoom (= curRoomPlane (curRoom plane?)))
				(= curRoomPlaneLeft (curRoomPlane left:))
				(= curRoomPlaneTop (curRoomPlane top?))
				(= theLastScreenX (curRoomPlane right:))
				(= theLastScreenY (curRoomPlane bottom?))
			)
			(else
				(= curRoomPlaneLeft 0)
				(= curRoomPlaneTop 0)
				(= theLastScreenX lastScreenX)
				(= theLastScreenY lastScreenY)
			)
		)
		(= temp0 (- right left))
		(= temp1 (- bottom top))
		(if (== param1 -1)
			(= param1
				(+
					curRoomPlaneLeft
					(/ (- (- theLastScreenX curRoomPlaneLeft) temp0) 2)
				)
			)
		else
			(= param1 (+ param1 curRoomPlaneLeft))
		)
		(if (== param2 -1)
			(= param2
				(+
					curRoomPlaneTop
					(/ (- (- theLastScreenY curRoomPlaneTop) temp1) 2)
				)
			)
		else
			(= param2 (+ param2 curRoomPlaneTop))
		)
		(self
			setRect: param1 param2 (+ param1 temp0) (+ param2 temp1)
		)
	)
	
	(method (scaleBitmap theTheLeft theTheTop theTheRight theTheBottom &tmp temp0 temp1 temp2 temp3 temp4 temp5 temp6 theLeft theTop theRight theBottom)
		(if argc
			(= theLeft theTheLeft)
			(= theTop theTheTop)
			(= theRight theTheRight)
			(= theBottom theTheBottom)
		else
			(= theLeft left)
			(= theTop top)
			(= theRight right)
			(= theBottom bottom)
		)
		(= temp1
			(CelWide (bitmap view?) (bitmap loop?) (bitmap cel?))
		)
		(= temp3
			(CelHigh (bitmap view?) (bitmap loop?) (bitmap cel?))
		)
		(= temp0 (+ (- theRight theLeft) 1))
		(= temp2 (+ (- theBottom theTop) 1))
		(if (< temp0 250)
			(= temp4 (/ (* temp0 128) temp1))
		else
			(= temp4 (* (/ (* (/ temp0 2) 128) temp1) 2))
		)
		(= temp5 (/ (* temp2 128) temp3))
		(bitmap scaleSignal: 1 scaleX: temp4 scaleY: temp5)
		(UpdateScreenItem bitmap)
	)
	
	(method (setBitmap param1 param2 param3 param4 &tmp newCast temp1 temp2 temp3)
		(= temp3 0)
		(if bitmap
			(bitmap dispose:)
			(= newCast (self findCast: {planeBM}))
		else
			((= newCast (Cast new:)) name: {planeBM})
			(self addCast: newCast)
		)
		(if (< argc 3)
			((= bitmap (param1 new:)) posn: 0 0 init: newCast)
			(if (> argc 1) (= temp3 param2))
		else
			((= bitmap (View new:))
				view: param1
				loop: param2
				cel: param3
				posn: 0 0
				init: newCast
			)
			(if (> argc 3) (= temp3 param4))
		)
		(= temp1
			(CelWide (bitmap view?) (bitmap loop?) (bitmap cel?))
		)
		(= temp2
			(CelHigh (bitmap view?) (bitmap loop?) (bitmap cel?))
		)
		(if temp3
			(self scaleBitmap:)
		else
			(self setRect: left top (+ left temp1) (+ top temp2))
		)
	)
	
	(method (setInsetRect theInLeft theInTop theInRight theInBottom)
		(= inLeft theInLeft)
		(= inTop theInTop)
		(= inRight theInRight)
		(= inBottom theInBottom)
	)
	
	(method (setRect theLeft theTop theRight theBottom param5)
		(= left theLeft)
		(= top theTop)
		(= right theRight)
		(self
			setInsetRect: theLeft theTop theRight (= bottom theBottom)
		)
		(if (and bitmap (& (bitmap scaleSignal?) $0001))
			(self scaleBitmap:)
		)
		(if (and (> argc 4) param5) (UpdatePlane self))
	)
	
	(method (setSize &tmp temp0 temp1 castsNextNode temp3 temp4 temp5)
		(= temp0 (= temp1 0))
		(= castsNextNode (KList 3 (casts elements?)))
		(while castsNextNode
			(casts nextNode: (KList 6 castsNextNode))
			(= temp4 (KList 8 castsNextNode))
			(= temp3 (KList 3 (temp4 elements?)))
			(while temp3
				(temp4 nextNode: (KList 6 temp3))
				(if
				(> ((= temp5 (KList 8 temp3)) nsRight?) temp0)
					(= temp0 (temp5 nsRight?))
				)
				(if (> (temp5 nsBottom?) temp1)
					(= temp1 (temp5 nsBottom?))
				)
				(= temp3 (temp4 nextNode?))
			)
			(= castsNextNode (casts nextNode?))
		)
		(self setRect: left top (+ left temp0) (+ top temp1))
	)
	
	(method (setTitle param1 &tmp [temp0 2] newCast newIntArray temp4 temp5 castsNextNode temp7 temp8 temp9 newDText temp11 temp12 temp13)
		(self
			addCast: ((= newCast (Cast new:)) name: {titleCast} yourself:)
		)
		((= newDText (DText new:))
			fore: 255
			back: 0
			font: 0
			text: param1
			posn: 0 0
		)
		(= newIntArray (IntArray new:))
		(KText 0 (newIntArray data?) param1 0 -1)
		(= temp4 (+ (newIntArray at: 3) 12))
		(newDText
			nsLeft: 0
			nsTop: 0
			nsRight: (- right left)
			nsBottom: temp4
		)
		(= temp11 (- (newDText nsRight?) (newDText nsLeft?)))
		(= temp13
			(/
				(-
					temp11
					(= temp12 (- (newIntArray at: 2) (newIntArray at: 0)))
				)
				2
			)
		)
		(newDText
			textLeft: temp13
			textTop: 2
			textRight: (+ temp13 temp12 8)
			textBottom: temp4
			init: newCast
		)
		(newIntArray dispose:)
		(= temp5 (+ (- bottom top) 1))
		(if (< top temp4)
			(= top 0)
		else
			(= top (- top temp4))
		)
		(self setRect: left top right (+ top temp5 temp4) 1)
		(= castsNextNode (KList 3 (casts elements?)))
		(while castsNextNode
			(casts nextNode: (KList 6 castsNextNode))
			(= temp8 (KList 8 castsNextNode))
			(= temp7 (KList 3 (temp8 elements?)))
			(while temp7
				(temp8 nextNode: (KList 6 temp7))
				(if (!= (= temp9 (KList 8 temp7)) newDText)
					(temp9
						y: (+ (temp9 y?) temp4)
						nsTop: (+ (temp9 nsTop?) temp4)
						nsBottom: (+ (temp9 nsBottom?) temp4)
					)
					(UpdateScreenItem temp9)
				)
				(= temp7 (temp8 nextNode?))
			)
			(= castsNextNode (casts nextNode?))
		)
	)
	
	(method (addFeature)
		(if (and argc theFtrs) (theFtrs add: &rest))
	)
	
	(method (deleteFeature)
		(if (not theFtrs) (return))
		(theFtrs delete: &rest)
	)
	
	(method (addExtMH)
		(if argc
			(if (not extMH) (= extMH (EventHandler new:)))
			(extMH add: &rest)
		)
	)
	
	(method (deleteExtMH)
		(if (not extMH) (return))
		(extMH delete: &rest)
		(if (not (extMH size:)) (extMH dispose:) (= extMH 0))
	)
	
	(method (handleEvent event)
		(if (event claimed?) (return (event claimed?)))
		(if (and extMH (extMH handleEvent: event)) (return 1))
		(return
			(if (and (user input?) (& (event type?) $4000))
				(cond 
					(useSortedFeatures
						(OnMeAndLowY init:)
						(if (not (casts isKindOf: Cast))
							(casts eachElementDo: #eachElementDo 99 OnMeAndLowY event)
						else
							(casts eachElementDo: #perform OnMeAndLowY event)
						)
						(theFtrs eachElementDo: #perform OnMeAndLowY event)
						(if
							(and
								(OnMeAndLowY theObj?)
								((OnMeAndLowY theObj?) handleEvent: event)
							)
							(return 1)
						)
					)
					((casts handleEvent: event) (return 1))
					((theFtrs handleEvent: event) (return 1))
				)
				(if
					(and
						(not (event claimed?))
						(regions handleEvent: event)
					)
					(return 1)
				)
			else
				0
			)
		)
	)
)
