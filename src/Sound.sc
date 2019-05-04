;;; Sierra Script 1.0 - (do not remove this comment)
(script# 989)
(include system.sh) (include sci2.sh)
(use Main)
(use System)


(class Sound of Object
	(properties
		nodePtr 0
		handle 0
		flags $0000
		number 0
		vol 127
		priority 0
		loop 1
		signal $0000
		prevSignal 0
		dataInc 0
		min 0
		sec 0
		frame 0
		client 0
		owner 0
	)
	
	(method (new param1)
		((super new:) owner: (if argc param1 else 0) yourself:)
	)
	
	(method (init)
		(= prevSignal (= signal 0))
		(sounds add: self)
		(DoSound InitSound self)
	)
	
	(method (dispose)
		(sounds delete: self)
		(if nodePtr (DoSound KillSound self) (= nodePtr 0))
		(super dispose:)
	)
	
	(method (play theClient &tmp theParamTotal)
		(= theParamTotal argc)
		(if (and argc (IsObject [theClient (- argc 1)]))
			(= client [theClient (= theParamTotal (- argc 1))])
		else
			(= client 0)
		)
		(self init:)
		(if (not loop) (= loop 1))
		(if theParamTotal (= vol theClient) else (= vol 127))
		(DoSound PlaySound self 0)
	)
	
	(method (stop)
		(if handle (DoSound UpdateCues self) (DoSound StopSound self))
	)
	
	(method (pause param1)
		(if (not argc) (= param1 1))
		(DoSound
			PauseSound
			(if (self isMemberOf: Sound) self else 0)
			param1
		)
	)
	
	(method (hold param1)
		(if (not argc) (= param1 1))
		(DoSound HoldSound self param1)
	)
	
	(method (release)
		(DoSound HoldSound self 0)
	)
	
	(method (fade theClient param2 param3 param4 &tmp theParamTotal)
		(= theParamTotal argc)
		(if (and argc (IsObject [theClient (- argc 1)]))
			(= client [theClient (= theParamTotal (- argc 1))])
		)
		(if theParamTotal
			(DoSound
				FadeSound
				self
				theClient
				param2
				param3
				param4
			)
		else
			(DoSound FadeSound self 0 25 10 1)
		)
	)
	
	(method (mute param1)
		(if (not argc) (= param1 1))
		(DoSound MuteSound self param1)
	)
	
	(method (setVol param1)
		(DoSound SetVol self param1)
	)
	
	(method (setPri param1)
		(DoSound SetPri self param1)
	)
	
	(method (setLoop param1)
		(DoSound SetLoop self param1)
	)
	
	(method (send param1 param2 param3 param4)
		(if (and (<= 1 param1) (<= param1 15))
			(if (< param2 128)
				(DoSound MidiSend self param1 176 param2 param3)
			else
				(DoSound MidiSend self param1 param2 param3 param4)
			)
		)
	)
	
	(method (check)
		(if handle (DoSound UpdateCues self))
		(if signal
			(= prevSignal signal)
			(= signal 0)
			(if (IsObject client) (client cue: self))
		)
	)
	
	(method (clean param1)
		(if (or (not owner) (== owner param1))
			(self dispose:)
		)
	)
	
	(method (playBed theClient &tmp theParamTotal)
		(= theParamTotal argc)
		(if (and argc (IsObject [theClient (- argc 1)]))
			(= client [theClient (= theParamTotal (- argc 1))])
		else
			(= client 0)
		)
		(self init:)
		(if (not loop) (= loop 1))
		(if theParamTotal (= vol theClient) else (= vol 127))
		(DoSound PlaySound self 1)
	)
	
	(method (changeState)
		(DoSound ChangeSndState self)
	)
)
