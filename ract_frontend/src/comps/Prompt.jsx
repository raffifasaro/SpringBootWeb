import React, { useState } from 'react';
function Prompt({ onSubmit, onTimeSubmit, date, inputValue, timeValue }) { // Receive inputValue as prop
    const [internalInputValue, setInternalInputValue] = useState(inputValue); // Initialize with prop value
    const [internalTimeValue1, setInternalTimeValue1] = useState(timeValue); // Initialize time with prop value
    const [internalTimeValue2, setInternalTimeValue2] = useState(timeValue)

    const handleChange = (e) => {
        setInternalInputValue(e.target.value);
    };

    const handleTimeChange1 = (x) => {
        setInternalTimeValue1(x.target.value);
    };

    const handleTimeChange2 = (x) => {
        setInternalTimeValue2(x.target.value);
    };

    const handleSubmit = () => {
        onSubmit(internalInputValue); // Pass internalInputValue
        fetch('http://localhost:8080/endpoint2?dateValue=' + date + '&timeValue=' +
            internalTimeValue1 + ':' + internalTimeValue2 + '&text='
                     + internalInputValue)
                     .then(data => console.log(data))
                     .catch(error => console.error('Error:', error));
    };

    const handleTimeSubmit = () => {
        onTimeSubmit(internalTimeValue1);
        onTimeSubmit(internalTimeValue2)
    };

    return (
        <form className="box">
            <div className="field">
                <div className="align">
                    <div className="divider">Event on {date} at</div>
                    <div className="time-input">
                        <input className="input" type="text" placeholder="e.g. 8" value={internalTimeValue1}
                               onChange={handleTimeChange1}/>
                        <div className="divider">
                            :
                        </div>
                        <input className="input" type="text" placeholder="e.g. 45" value={internalTimeValue2}
                               onChange={handleTimeChange2}/>
                    </div>
                </div>
            </div>

            <div className="field">
                <div className="control">
                    <textarea className="input" type="text" value={internalInputValue} onChange={handleChange} style={{ height: '200px'}}/>
                </div>
            </div>

            <button onClick={() => {
                handleSubmit();
                handleTimeSubmit();
            }} className="button is-primary">Submit</button>
        </form>
    );
}

export default Prompt;

