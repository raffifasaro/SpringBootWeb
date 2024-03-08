import React, { useState } from 'react';
function Prompt({ onSubmit, onTimeSubmit, date, inputValue, timeValue }) { // Receive inputValue as prop
    const [internalInputValue, setInternalInputValue] = useState(inputValue); // Initialize with prop value
    const [internalTimeValue, setInternalTimeValue] = useState(timeValue); // Initialize time with prop value

    const handleChange = (e) => {
        setInternalInputValue(e.target.value);
    };

    const handleTimeChange = (x) => {
        setInternalTimeValue(x.target.value);
    };

    const handleSubmit = () => {
        onSubmit(internalInputValue); // Pass internalInputValue

        const eventObj = {
            date: date,
            time: internalTimeValue,
            text: internalInputValue
        };

        fetch('http://localhost:8080/endpoint2?dateValue=' + date + '&timeValue=' + internalTimeValue + '&text='
                     + internalInputValue)
                     .then(data => console.log(data))
                     .catch(error => console.error('Error:', error));

        //fetch('http://localhost:8080/endpoint?dateValue=' + date + '&timeValue=' + internalTimeValue + '&text='
        //             + internalInputValue)
        //             .then(data => console.log(data))
        //             .catch(error => console.error('Error:', error));
    };

    const handleTimeSubmit = () => {
        onTimeSubmit(internalTimeValue);
    };

    return (
        <form className="box">
            <h1 className="label">Event on {date}:</h1>
            <div className="field">
                <label className="label">Time:</label>
                <div className="control">
                    <input className="input" type="text" placeholder="e.g. 19:45" value={internalTimeValue} onChange={handleTimeChange}/>
                </div>
            </div>

            <div className="field">
                <label className="label">Event on {date} at {internalTimeValue}:</label>
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

    //             <div className="field">
    //                 <label className="label">Event on {date} at {time_value}:</label>
    //                 <div className="control">
    //                     <input type="text" value={internalInputValue} onChange={handleChange}/>
    //                 </div>
    //             </div>

    //         <div>
    //             <div>Event on {date}:</div>
    //             <input type="text" value={internalInputValue} onChange={handleChange} />
    //             <button onClick={handleSubmit}>Submit</button>
    //         </div>

}

export default Prompt;

